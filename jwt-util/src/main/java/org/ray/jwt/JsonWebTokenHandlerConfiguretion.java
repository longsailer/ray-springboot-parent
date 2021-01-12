package org.ray.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.ray.jwt.token.DefaultJsonWebTokenHandler;

/**
 * JsonWebTokenHandlerConfiguretion.java
 * <br><br>
 * JWT自动配置类，把默认的Handler注入容器
 * @author: ray
 * @date: 2020年6月30日
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(StringRedisTemplate.class)
@Configuration
public class JsonWebTokenHandlerConfiguretion {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Value("${jwt.redis.systemId}")
	private String systemId;

	@Bean
	public DefaultJsonWebTokenHandler defaultHandler(){
		return new DefaultJsonWebTokenHandler(stringRedisTemplate, systemId);
	}
}

