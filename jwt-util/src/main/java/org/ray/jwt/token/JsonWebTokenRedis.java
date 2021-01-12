package org.ray.jwt.token;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * JsonWebTokenRedis.java <br>
 * <br>
 * 对token进行存储，该类主要提供存储到redis中
 * 
 * @author: ray
 * @date: 2020年6月29日
 */
public class JsonWebTokenRedis {
	public static final String JWT_REDIS_PREFFIX = "JWT:AUTHTOKEN:";
	private StringRedisTemplate stringRedisTemplate;
	private BoundHashOperations<String, String, String> ops;

	public JsonWebTokenRedis(StringRedisTemplate stringRedisTemplate, String systemId) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.ops(systemId);
	}

	private BoundHashOperations<String, String, String> ops(String systemId) {
		this.ops = this.stringRedisTemplate.boundHashOps(JWT_REDIS_PREFFIX + systemId);
		return this.ops;
	}

	public void set(String userId, String token) {
		this.ops.put(userId, token);
	}

	public String get(String userId) {
		return this.ops.get(userId);
	}
}