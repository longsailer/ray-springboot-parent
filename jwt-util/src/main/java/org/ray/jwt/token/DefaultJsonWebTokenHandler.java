package org.ray.jwt.token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.ray.encrypt.utils.AES128Encryption;
import org.ray.jwt.json.JsonMapper;
import org.ray.jwt.json.SpringObjectMapper;

/**
 * DefaultJsonWebTokenHandler.java
 * <br><br>
 * JWT默认处理实现类
 * @author: ray
 * @date: 2020年6月29日
 */

public class DefaultJsonWebTokenHandler implements JsonWebTokenHandler{
	private Logger logger = LoggerFactory.getLogger(DefaultJsonWebTokenHandler.class);
	private JsonMapper<JsonWebToken> jsonMapper = new SpringObjectMapper();
	private JsonWebTokenRedis jsonWebTokenRedis;
	
	public DefaultJsonWebTokenHandler(StringRedisTemplate stringRedisTemplate, String systemId){
		this.jsonWebTokenRedis = new JsonWebTokenRedis(stringRedisTemplate, systemId);
	}
	
	public String createToken(String userId, String userName, String remoteIp, String userAgent){
		try {
			return JsonWebTokenFactory.createToken(userId, userName, remoteIp, userAgent);
		} catch (Exception e) {
			logger.error("创建Token时发生错误.", e);
			return null;
		}
	}
	
	public boolean validateToken(String userId, String authToken, String remoteIp, String userAgent){
		String redisToken = this.jsonWebTokenRedis.get(userId);
		return JsonWebTokenFactory.validateToken(userId, authToken, remoteIp, userAgent, redisToken);
	}

	public String getToken(String userId){
		String authToken = this.getRedisStringToken(userId);
		if(StringUtils.isNotEmpty(authToken)){
			return authToken.split(",")[0];
		}
		return authToken;
	}
	
	public JsonWebToken getUserToken(String userId){
		return getRedisToken(userId);
	}
	
	private JsonWebToken getRedisToken(String userId){
		if(StringUtils.isEmpty(userId)){
			return null;
		}
		String redisToken = this.getRedisStringToken(userId);
		if(StringUtils.isEmpty(redisToken)){
			return null;
		}
		String _authToken = redisToken.split(",")[0];
		String hiddenToken = redisToken.split(",")[1];
		try {
			JsonWebToken _token = jsonMapper.jsonToObject(AES128Encryption.decrypt(hiddenToken, _authToken));
			_token.getPayLoad().setToken(hiddenToken);
			return _token;
		} catch (Exception e) {
			logger.error("解析用户的authToken时发生错误，该用户未登录", e);
			return null;
		}
	}
	
	private String getRedisStringToken(String userId){
		String redisToken = jsonWebTokenRedis.get(userId);
		if(StringUtils.isEmpty(redisToken)){
			return null;
		}
		return redisToken;
	}
}

