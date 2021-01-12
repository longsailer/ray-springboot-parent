package org.ray.operation.info;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-11 09:28
 * @Description: 用户信息
 */
public class UserInfoDto {


    /**
     * 用户id
     */
    private String userId;

    /**
     * 登陆名字
     */
    private String loginName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
