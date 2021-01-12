package org.ray.operation.info;

import java.io.Serializable;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-04 13:38
 * @Description: 日志信息-查询条件
 */
public class LogInfoDto implements Serializable {


    /**
     * 用户id
     */
    private String userId;

    /**
     * 登陆名字
     */
    private String loginName;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 错误信息
     */
    private String errorInfo;

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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
