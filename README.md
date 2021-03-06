```
本范例项目异常链接地址：
https://github.com/spring-projects/spring-security-oauth/issues/1471  

# spring-boot-cloud-oauth2-demo


如何使用终端命令去调用测试Spring Boot2 Cloud OAuth2 JWT授权服务器接口生成Token值
============================================================================

客户端的授权模式
授权码模式（authorization code）

浏览器结合终端命令调用授权服务器范例1：

第1步：使用浏览器直接访问以下链接地址获得一个一次性临时code值
http://localhost:8081/uaa/oauth/authorize?response_type=code&scope=read write foo&client_id=fooClientId&redirect_uri=http://www.baidu.com/&state=x1y1

第2步：自动跳转到登录链接http://localhost:8081/uaa/login
在浏览器登录页面输入用户名称和用户密码
用户名称:john
用户密码:123

当我们登录成功后，自动跳转到如下链接地址
http://localhost:8081/uaa/oauth/authorize?response_type=code&scope=read%20write%20foo&client_id=fooClientId&redirect_uri=http://www.baidu.com/&state=x1y1
页面
Do you authorize 'fooClientId' to access your protected resources?
点击Authorize按钮
从浏览器地址栏中复制出回调返回的code参数值粘贴到终端命令中,例如：回调链接地址中code=VXB8gE

第3步：使用一次性临时code值与授权服务器交换获得一个token值

终端命令调用授权服务器范例1：
fooClientId:secret 键-值对对应的BASE64编码 Zm9vQ2xpZW50SWQ6c2VjcmV0

curl -i -X POST http://localhost:8081/uaa/oauth/token \
-H 'authorization: Basic Zm9vQ2xpZW50SWQ6c2VjcmV0' \
-H "Accept: application/json" \
-d "client_id=fooClientId&client_secret=secret&grant_type=authorization_code&code=VXB8gE&redirect_uri=http://www.baidu.com/"


浏览器结合终端命令调用授权服务器范例2：

第1步：使用浏览器直接访问以下链接地址获得一个一次性临时code值
http://localhost:8081/uaa/oauth/authorize?response_type=code&scope=read write bar&client_id=barClientId&redirect_uri=http://www.baidu.com/&state=x2y2

第2步：自动跳转到登录链接http://localhost:8081/uaa/login
在浏览器登录页面输入用户名称和用户密码
用户名称:john
用户密码:123

当我们登录成功后，自动跳转到如下链接地址
http://localhost:8081/uaa/oauth/authorize?response_type=code&scope=read%20write%20bar&client_id=barClientId&redirect_uri=http://www.baidu.com/&state=x2y2
页面
Do you authorize 'barClientId' to access your protected resources?
点击Authorize按钮

从浏览器地址栏中复制出回调返回的code参数值粘贴到终端命令中,例如：回调链接地址中code=VnESYV

第3步：使用一次性临时code值与授权服务器交换获得一个token值

终端命令调用授权服务器范例2：
barClientId:secret 键-值对对应的BASE64编码 YmFyQ2xpZW50SWQ6c2VjcmV0

curl -i -X POST http://localhost:8081/uaa/oauth/token \
-H 'authorization: Basic YmFyQ2xpZW50SWQ6c2VjcmV0' \
-H "Accept: application/json" \
-d "client_id=barClientId&client_secret=secret&grant_type=authorization_code&code=VnESYV&redirect_uri=http://www.baidu.com/"


======================================================================================


客户端的授权模式
简化模式（implicit）:
response_type：表示授权类型，此处的值固定为"token"，必选项。
直接在浏览器中向授权服务器申请令牌

第1步：直接在浏览器里输入以下链接地址
http://localhost:8081/uaa/oauth/authorize?response_type=token&client_id=sampleClientId&redirect_uri=http://www.baidu.com/&scope=read write foo bar&state=123

第2步：自动跳转到登录链接http://localhost:8081/uaa/login
在浏览器登录页面输入用户名称和用户密码
用户名称:john
用户密码:123

第3步：当我们登录成功后，自动跳转到以下链接地址页面
http://localhost:8081/uaa/oauth/authorize?response_type=token&client_id=sampleClientId&redirect_uri=http://www.baidu.com/&scope=read write foo bar&state=123

Do you authorize 'sampleClientId' to access your protected resources?
点击Authorize按钮
 
当授权操作成功后回调返回包含token值链接地址
https://www.baidu.com/#access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzU5MDgzNzMsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZDU5ZGNmYjAtNzU5Zi00MmRlLWI5MTctZDYwYjM4OTJmMTQ1IiwiY2xpZW50X2lkIjoic2FtcGxlQ2xpZW50SWQiLCJzY29wZSI6WyJiYXIiLCJmb28iLCJyZWFkIiwid3JpdGUiXX0.pgJBYvugTYMt_urDfhg5s6e7hletN6IFCji1XdEuC4s&token_type=bearer&state=123&expires_in=3599&jti=d59dcfb0-759f-42de-b917-d60b3892f145


======================================================================================


客户端的授权模式
密码模式（Resource Owner Password Credentials Grant）

终端命令调用授权服务器范例1：
fooClientId:secret 键-值对对应的BASE64编码 Zm9vQ2xpZW50SWQ6c2VjcmV0

curl -i -X POST http://localhost:8081/uaa/oauth/token \
-H 'Content-Type: application/x-www-form-urlencoded' \
-H 'Authorization: Basic Zm9vQ2xpZW50SWQ6c2VjcmV0' \
-d 'grant_type=password&username=john&password=123'


终端命令调用授权服务器范例2：
barClientId:secret 键-值对对应的BASE64编码 YmFyQ2xpZW50SWQ6c2VjcmV0

curl -i -X POST http://localhost:8081/uaa/oauth/token \
-H 'Content-Type: application/x-www-form-urlencoded' \
-H 'Authorization: Basic YmFyQ2xpZW50SWQ6c2VjcmV0' \
-d 'grant_type=password&username=john&password=123'


======================================================================================


客户端的授权模式
客户端模式（client credentials）

终端命令调用授权服务器范例1：
applicationClientId:secret 键-值对对应的BASE64编码 YXBwbGljYXRpb25DbGllbnRJZDpzZWNyZXQ=

curl -i -X POST http://localhost:8081/uaa/oauth/token \
-H 'authorization: Basic YXBwbGljYXRpb25DbGllbnRJZDpzZWNyZXQ=' \
-H 'cache-control: no-cache' \
-H 'content-type: application/x-www-form-urlencoded' \
-d 'grant_type=client_credentials&client_id=applicationClientId&client_secret=secret'




*********************************************************************************
**************************************************************************************


本范例反序列化spring boot 2.x/1.x 不支持，需要解决如下问题

private OAuth2AccessToken deserializeAccessToken(byte[] bytes) {
	return oauth2AccessTokenSerialization.deserialize(bytes);
}

when deserializing json to OAuth2AccessToken is OK!!!

==============================================
private OAuth2Authentication deserializeAuthentication(byte[] bytes) {
return oauth2AuthenticationSerialization.deserialize(bytes);
}

when deserializing json to OAuth2Authentication is failed!!!

如果你们能解决这个问题 Token / JWT Token 就能跨语言共享啦
If you can solve this problem, Token / JWT Token can be shared across languages.



Client Credentials Mode
Example1：
applicationClientId:secret 's key-value BASE64 code is YXBwbGljYXRpb25DbGllbnRJZDpzZWNyZXQ=

curl -i -X POST http://localhost:8081/uaa/oauth/token
-H 'authorization: Basic YXBwbGljYXRpb25DbGllbnRJZDpzZWNyZXQ='
-H 'cache-control: no-cache'
-H 'content-type: application/x-www-form-urlencoded'
-d 'grant_type=client_credentials&client_id=applicationClientId&client_secret=secret'

[root@contoso ~]# redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> monitor
OK
1536174794.483180 [0 127.0.0.1:35750] "GET" ""auth_to_access:c474a00eab4c206088ae126b00cece38""
1536174931.358496 [0 127.0.0.1:35750] "SET" ""access:94c8bfe3-8247-4429-a910-4bef74d23232"" "{"access_token":"94c8bfe3-8247-4429-a910-4bef74d23232","token_type":"bearer","expires_in":43180,"scope":"read write foo bar"}"
1536174931.360426 [0 127.0.0.1:35750] "SET" ""auth:94c8bfe3-8247-4429-a910-4bef74d23232"" "{"authorities":[],"details":null,"authenticated":true,"userAuthentication":null,"oauth2Request":{"clientId":"applicationClientId","scope":["read","write","foo","bar"],"requestParameters":{"grant_type":"client_credentials","client_id":"applicationClientId"},"resourceIds":[],"authorities":[],"approved":true,"refresh":false,"redirectUri":null,"responseTypes":[],"extensions":{},"refreshTokenRequest":null,"grantType":"client_credentials"},"principal":"applicationClientId","clientOnly":true,"credentials":"","name":"applicationClientId"}"
1536174931.360700 [0 127.0.0.1:35750] "SET" ""auth_to_access:c474a00eab4c206088ae126b00cece38"" "{"access_token":"94c8bfe3-8247-4429-a910-4bef74d23232","token_type":"bearer","expires_in":43180,"scope":"read write foo bar"}"
1536174931.362819 [0 127.0.0.1:35750] "RPUSH" ""client_id_to_access:applicationClientId"" "{"access_token":"94c8bfe3-8247-4429-a910-4bef74d23232","token_type":"bearer","expires_in":43180,"scope":"read write foo bar"}"
1536174931.368307 [0 127.0.0.1:35750] "EXPIRE" ""access:94c8bfe3-8247-4429-a910-4bef74d23232"" "43139"
1536174931.368404 [0 127.0.0.1:35750] "EXPIRE" ""auth:94c8bfe3-8247-4429-a910-4bef74d23232"" "43139"
1536174931.368413 [0 127.0.0.1:35750] "EXPIRE" ""auth_to_access:c474a00eab4c206088ae126b00cece38"" "43139"
1536174931.368419 [0 127.0.0.1:35750] "EXPIRE" ""client_id_to_access:applicationClientId"" "43139"
1536174931.368425 [0 127.0.0.1:35750] "EXPIRE" ""uname_to_access:applicationClientId:"" "43139"

above curl command the second time to execute
Could not read JSON: Cannot construct instance of org.springframework.security.oauth2.provider.OAuth2Authentication (no Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)



参考引用：https://www.baeldung.com/jackson-exception
```
