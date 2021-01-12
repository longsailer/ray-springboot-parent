/**
 * JsonWebToken.java
 * @author: 杨洲
 * @date: 2018年5月17日
 */
package org.ray.jwt.token;

import java.io.Serializable;
import java.util.UUID;

public class JsonWebToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4518458642945582532L;
	
	private Payload payLoad;
	private String sign;
	
	public JsonWebToken(){
		this.payLoad = new Payload();
	}
	
	public JsonWebToken(Payload payLoad){
		this.payLoad = payLoad;
	}
	
	public JsonWebToken(String userId, String userName, String remoteIp, String userAgent){
		this.payLoad = new Payload(userId, userName, remoteIp, userAgent);
	}
	
	public class Payload {
		private String userId;
		private String userName;
		private String token;
		private String remoteIp;
		private String userAgent;
		
		public Payload(){
			this.token = UUID.randomUUID().toString();
		}
		
		public Payload(String userId, String userName, String remoteIp, String userAgent){
			this();
			this.userId = userId;
			this.userAgent = userAgent;
			this.userName = userName;
			this.remoteIp = remoteIp;
		}
		
		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}
		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}
		/**
		 * @return the token
		 */
		public String getToken() {
			return token;
		}
		/**
		 * @param token the token to set
		 */
		public void setToken(String token) {
			this.token = token;
		}
		/**
		 * @return the remoteIp
		 */
		public String getRemoteIp() {
			return remoteIp;
		}
		/**
		 * @param remoteIp the remoteIp to set
		 */
		public void setRemoteIp(String remoteIp) {
			this.remoteIp = remoteIp;
		}
		/**
		 * @return the userAgent
		 */
		public String getUserAgent() {
			return userAgent;
		}
		/**
		 * @param userAgent the userAgent to set
		 */
		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}

		@Override
		public int hashCode() {
			return this.toString().hashCode();
		}

		@Override
		public String toString() {
			StringBuffer payLoad = new StringBuffer();
			payLoad.append(userId).append(userName).append(remoteIp).append(userAgent).append(token);
			return payLoad.toString();
		}
	}

	/**
	 * @return the payLoad
	 */
	public Payload getPayLoad() {
		return payLoad;
	}

	/**
	 * @param payLoad the payLoad to set
	 */
	public void setPayLoad(Payload payLoad) {
		this.payLoad = payLoad;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public int hashCode() {
		return this.sign.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JsonWebToken)
			return this.getSign().equals(((JsonWebToken)obj).getSign());
		else
			return super.equals(obj);
	}
}
