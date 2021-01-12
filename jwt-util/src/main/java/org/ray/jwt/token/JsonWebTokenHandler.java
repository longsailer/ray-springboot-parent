package org.ray.jwt.token;


/**
 * JsonWebTokenHandler.java
 * <br><br>
 * JWT处理接口，提供最基本的token方法：创建和验证
 * @author: ray
 * @date: 2020年6月30日
 */
public interface JsonWebTokenHandler {
	/**
	 * 根据用户id和用户名，以及客户端的IP和设备信息生成token
	 * @author ray
	 * @param userId
	 * @param userName
	 * @param remoteIp
	 * @param userAgent
	 * @return String
	 */
	public String createToken(String userId, String userName, String remoteIp, String userAgent);
	/**
	 * 通过客户端的IP和设备信息来验证token
	 * @author ray
	 * @param userId
	 * @param authToken
	 * @param remoteIp
	 * @param userAgent
	 * @return boolean
	 */
	public boolean validateToken(String userId, String authToken, String remoteIp, String userAgent);
}