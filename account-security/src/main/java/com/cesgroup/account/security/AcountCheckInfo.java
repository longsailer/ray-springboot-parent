package org.ray.account.security;

import java.io.Serializable;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-02 13:39
 * @Description: 登陆信息
 */
public class AcountCheckInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9014076076977957336L;

	/**
     * 用户名
     */
    private String userName;

    /**
     * 剩余次数
     */
    private Integer remainCount;

    /**
     * 初始化时间
     */
    private long createTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
