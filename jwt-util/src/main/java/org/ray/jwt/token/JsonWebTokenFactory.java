/**
 * JsonCertificateTokenFactory.java
 * @author: 杨洲
 * @date: 2018年5月18日
 */
package org.ray.jwt.token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ray.encrypt.utils.AES128Encryption;
import org.ray.encrypt.utils.SecretAlgorithm;
import org.ray.jwt.json.JsonMapper;
import org.ray.jwt.json.SpringObjectMapper;
/**
 * Token工厂,提供两个主要功能：创建token和验证token
 * @see JsonWebTokenFactory
 * @author ray
 * @date 2020-06-29 
 */
public class JsonWebTokenFactory {
	private static Logger logger = LoggerFactory.getLogger(JsonWebTokenFactory.class);
	private static JsonMapper<JsonWebToken> jsonMapper = new SpringObjectMapper();
	/**
	 * 创建token，当注意若外网IP发生涉繁变动的环境，会导致token失效
	 * @author ray
	 * @param userId 服务端获取
	 * @param userName 服务端获取
	 * @param remoteIp 客户端获取
	 * @param userAgent 客户端获取
	 * @throws Exception
	 * @return String
	 */
	public static String createToken(String userId, String userName, String remoteIp, String userAgent) throws Exception{
		JsonWebToken token = new JsonWebToken(userId, userName, remoteIp, userAgent);
		token.setSign(SecretAlgorithm.md5(token.getPayLoad().toString()));
		String hiddenToken = token.getPayLoad().getToken();
		token.getPayLoad().setToken("You will not know this token forever.");
		String authToken = AES128Encryption.encrypt(hiddenToken, jsonMapper.objectToJson(token));
		return authToken;
	}
	/**
	 * 验证token是否有效
	 * @author ray
	 * @param userId 客户端获取
	 * @param authToken 客户端获取
	 * @param remoteIp 客户端获取
	 * @param userAgent 客户端获取
	 * @param redisToken 服务端存储的值
	 * @return boolean
	 */
	public static boolean validateToken(String userId, String authToken, String remoteIp, String userAgent, String redisToken){
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(authToken) || StringUtils.isEmpty(remoteIp) || StringUtils.isEmpty(userAgent)){
			return false;
		}
		String _authToken = redisToken.split(",")[0];
		if(_authToken.compareTo(authToken) != 0){
			return false;
		}
		String hiddenToken = redisToken.split(",")[1];
		try{
			JsonWebToken _token = jsonMapper.jsonToObject(AES128Encryption.decrypt(hiddenToken, _authToken));
			JsonWebToken token = jsonMapper.jsonToObject(AES128Encryption.decrypt(hiddenToken, authToken));
			if(token == null || token.getPayLoad() == null || StringUtils.isEmpty(token.getSign())){
				return false;
			}
			if(!token.equals(_token)){
				return false;
			}
			JsonWebToken waitToken = new JsonWebToken(userId, token.getPayLoad().getUserName(), remoteIp, userAgent);
			waitToken.getPayLoad().setToken(hiddenToken);
			String waitSign = SecretAlgorithm.md5(waitToken.getPayLoad().toString());
			if(!waitSign.equals(_token.getSign())){
				return false;
			}
		}catch(Exception e){
			logger.error("解析用户的authToken时发生错误，用户未登录或token不合法", e);
			return false;
		}
		return true;
	}
}
