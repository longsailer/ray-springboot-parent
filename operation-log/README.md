## 操作日志通用包说明 ##


### 基础说明
- 通过拦截器进行操作日志的拦截 存储到 t_log_info
- 提供基础日志的查询方法(支持模糊、分页)

### pom依赖
- pom依赖<当前版本 0.0.1>

```xml 
        <dependency>
            <groupId>com.cesgroup</groupId>
            <artifactId>operation-log</artifactId>
            <version>0.0.1</version>
        </dependency>
```

### 使用说明
1. 实现操作日志存储：继承 LogAbstract 类，重写 getUser(用户信息) 和 createJdbcTemplate(自定义数据源) 方法
2. 日志查询接口：调用 LogInfoService.logInfoQuery() 方法 查询信息 , LogInfoService.logInfoTotal 查询 总计，支持模糊、分页功能
3. 初始化sql：

```sql 
CREATE TABLE `t_log_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userAgent` varchar(300) DEFAULT NULL COMMENT '浏览器信息',
  `userIp` varchar(30) DEFAULT NULL COMMENT '用户的ip',
  `userId` varchar(50) DEFAULT NULL COMMENT '用户的id',
  `loginName` varchar(30) DEFAULT NULL COMMENT '登陆人（操作人）',
  `requestUrl` varchar(300) DEFAULT NULL COMMENT '请求的url',
  `requestMethod` varchar(10) DEFAULT NULL COMMENT '请求方式（如 post、get...）',
  `className` varchar(50) DEFAULT NULL COMMENT '类名',
  `classDesc` varchar(50) DEFAULT NULL COMMENT '类的描述',
  `methodName` varchar(50) DEFAULT NULL COMMENT '方法名',
  `methodDesc` varchar(50) DEFAULT NULL COMMENT '方法描述',
  `errorInfo` text COMMENT '错误信息',
  `usedTime` bigint(20) DEFAULT NULL COMMENT '耗时',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户操作-日志信息存储表'
```

### 基础案例

```java 
	
	//日志信息存储
    ...
	    @Autowired
        private RedisTemplate redisTemplate;
    
        @Autowired
        private JdbcTemplate jdbcTemplate;
       
        public UserInfoDto getUser(HttpServletRequest request) {
            //用户信息
            ...
            if (user != null) {
                UserInfoDto userInfoDto = new UserInfoDto();
                userInfoDto.setUserId(user.getId());
                userInfoDto.setLoginName(user.getLoginName());
                return userInfoDto;
            }
            return null;
        }
    
        @Override
        public JdbcTemplate createJdbcTemplate() {
            return jdbcTemplate;
        }

    //日志信息查询
	...
        @Autowired
        private AccountSecurity accountSecurity;
	    ...
        @Autowired
        private LogInfoService logInfoService;
        
            public void logInfoQuery() {
                LogInfoDto logInfoDto=new LogInfoDto();
                ...
                List<LogInfo> logInfoList= logInfoService.logInfoQuery(logInfoDto,1,10);
                PageInfo<LogInfo> page = new PageInfo<>(list);
                page.setTotal(logInfoQueryService.logInfoTotal(logInfoDto));
                ...     
            }
	...
```