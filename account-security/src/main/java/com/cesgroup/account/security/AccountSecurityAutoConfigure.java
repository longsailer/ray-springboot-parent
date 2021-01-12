package org.ray.account.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 帐户安全机制保障
 * 
 * @author ray
 * @since 2019-06-26 11:18
 * 
 */

@Configuration
@ConditionalOnClass(JedisConnectionFactory.class)
public class AccountSecurityAutoConfigure {
	/**
     * redis存储key前缀
     */
    @Value("${as.key.prefix}")
    private String asKeyPrefix;
    /**
     * 超过失败次数后，帐户被锁定的时间
     */
    @Value("${as.locktime:2}")
    private Integer asLockTime;
    /**
     * 登陆失败次数 默认值 4
     */
    @Value("${as.loginfailcount:4}")
    private Integer asLoginFailCount;
	
	@Bean
	public AccountSecurityConfigProperties accountSecurityConfigProperties(){
		return new AccountSecurityConfigProperties(asKeyPrefix, asLockTime, asLoginFailCount);
	}
}
