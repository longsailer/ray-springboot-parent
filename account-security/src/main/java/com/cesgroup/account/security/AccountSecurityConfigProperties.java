/**
 * AccountSecurityConfigProperties.java
 * @author: 杨洲
 * @date: 2019年7月4日
 */
package org.ray.account.security;

public class AccountSecurityConfigProperties {
	/**
     * redis存储key前缀
     */
    private String asKeyPrefix;

    /**
     * 超过失败次数后，帐户被锁定的时间<br>
     * 单位：小时
     */
    private int asLockTime = 2;

    /**
     * 登陆失败次数 默认值 4<br>
     * 单位：次数
     */
    private int asLoginFailCount = 4;
    
    
    public AccountSecurityConfigProperties(String asKeyPrefix, int asLockTime, int asLoginFailCount){
    	this.asKeyPrefix = asKeyPrefix;
    	this.asLockTime = asLockTime;
    	this.asLoginFailCount = asLoginFailCount;
    }

	public String getAsKeyPrefix() {
		return asKeyPrefix;
	}

	public void setAsKeyPrefix(String asKeyPrefix) {
		this.asKeyPrefix = asKeyPrefix;
	}

	public Integer getAsLockTime() {
		return asLockTime;
	}

	public void setAsLockTime(Integer asLockTime) {
		this.asLockTime = asLockTime;
	}

	public Integer getAsLoginFailCount() {
		return asLoginFailCount;
	}

	public void setAsLoginFailCount(Integer asLoginFailCount) {
		this.asLoginFailCount = asLoginFailCount;
	}
}
