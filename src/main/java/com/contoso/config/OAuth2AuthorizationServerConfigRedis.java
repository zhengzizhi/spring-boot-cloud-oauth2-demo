package com.contoso.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigRedis extends AuthorizationServerConfigurerAdapter {

	@Autowired(required = true)
	private TokenStore redisTokenStore;

	@Resource
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	public TokenStore redisTokenStore() {// 01
		CustomRedisTokenStore redisTokenStore = new CustomRedisTokenStore(redisConnectionFactory);
		return redisTokenStore;
	}

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
		clients.inMemory().withClient("sampleClientId").authorizedGrantTypes("implicit")
				.scopes("read", "write", "foo", "bar").autoApprove(false).accessTokenValiditySeconds(3600)
				.redirectUris("http://localhost:8083/", "http://www.baidu.com/").and().withClient("applicationClientId")
				.secret(passwordEncoder().encode("secret")).authorizedGrantTypes("client_credentials")
				.scopes("read", "write", "foo", "bar").and().withClient("fooClientId")
				.secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("foo", "read", "write")
				.accessTokenValiditySeconds(3600) // 1 hour
				.refreshTokenValiditySeconds(2592000) // 30 days
				.redirectUris("http://localhost:8089/", "http://www.baidu.com/").and().withClient("barClientId")
				.secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("bar", "read", "write")
				.accessTokenValiditySeconds(3600) // 1 hour
				.refreshTokenValiditySeconds(2592000) // 30 days
				.redirectUris("http://localhost:8089/", "http://www.baidu.com/").and()
				.withClient("testImplicitClientId").authorizedGrantTypes("implicit")
				.scopes("read", "write", "foo", "bar").autoApprove(true).redirectUris("xxx");
	} // @formatter:on

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() { // 03
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(redisTokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		// @formatter:off
		endpoints // 02
				.tokenStore(redisTokenStore).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				.authenticationManager(authenticationManager);
	} // @formatter:on

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
