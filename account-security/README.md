## 帐户安全机制说明 ##
***

### 基础说明
- 用户在登录时，输入用户名和密码的错误提示：“用户名或密码错误”，同时应当提示可以“错误的次数”，超过次数将会锁定帐户一个周期（周期不少于2小时），在该周期内，不可再次登录。
- 每个管理类系统需按要求进行开发或修改登录逻辑

### 提示语如下
1. 用户名或密码错误，您可以再尝试**4**次，超出次数您的帐户将会被锁定
2. 用户名或密码错误，您可以再尝试**3**次
3. 您的帐户已被锁定，请**2**个小时之后，再次重试

### pom依赖以及提供3个方法
- pom依赖<当前版本 0.0.1>

```xml 
        <dependency>
            <groupId>com.cesgroup</groupId>
            <artifactId>account-security</artifactId>
            <version>0.0.1</version>
        </dependency>
```
1. checkAccount 检查当前帐户是否被锁定的方法，返回 Boolean 类型，true 则该账号未被锁定，false 则该账号被锁定
-调用方法如下：
`Boolean isLock=  accountSecurity.checkAccount(username);`
2. loginFailureCallback 用户名密码错误时回调的方法，返回 Integer 类型，该账号剩余可重试次数 
-调用方法如下：
`Integer remainCounts= accountSecurity.loginFailureCallback(username);`
3. loginSuccessCallback 登录成功后回调的方法，无返回值，成功之后 delete
-调用方法如下：
`accountSecurity.loginSuccessCallback(username);`

### 配置参数说明
- as.key.prefix - 必填   redis存储前缀，必须提前在配置文件里赋值
- as.locktime - 不必填 redis锁定时间（单位：小时），默认2小时
- as.loginfailcount - 不必填 redis可重试次数（单位：次数），默认4次

### 基础案例

```java 
	...
    @Autowired
    private AccountSecurity accountSecurity;
	
	...
	
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        
		//登陆检查账号有没有被锁
        if (!accountSecurity.checkAccount(username)) {
            return CommonUtils.fail(1001, "您的帐户已被锁定，请2个小时之后，再次重试");
        }
		
        if (...) {
            //登录验证成功
            accountSecurity.loginSuccessCallback(username);
        } else {
            //登录验证失败，会记录次数，每失败一次，次数减1
            Integer remainCounts = accountSecurity.loginFailureCallback(username);
			//当次数为0时，锁定帐户
            if (null != remainCounts && 0 < remainCounts) {
                return CommonUtils.fail(1001, "用户名或密码错误，您可以再尝试" + remainCounts + "次，超出次数您的帐户将会被锁定");
            } else {
                return CommonUtils.fail(1001, "您的帐户已被锁定，请2个小时之后，再次重试");
            }
        }
        return CommonUtils.success(1000, "登陆成功");
    }
	...
```