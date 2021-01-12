package org.ray.operation.info;

import java.io.Serializable;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-04 13:38
 * @Description: 日志信息-数据库信息
 */
public class LogInfo implements Serializable {


    /**
     * 主键
     */
    private long id;

    /**
     * 浏览器信息
     */
    private String userAgent;

    /**
     * 用户ip
     */
    private String userIp;

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
     * 请求方式
     */
    private String requestMethod;

    /**
     * 类名
     */
    private String className;

    /**
     * 类描述
     */
    private String classDesc;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法描述
     */
    private String methodDesc;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 耗时
     */
    private long usedTime;

    /**
     * 创建时间
     */
    private String createTime;

    public LogInfo(long id, String userAgent, String userIp, String userId, String loginName, String requestUrl, String requestMethod, String className, String classDesc, String methodName, String methodDesc, String errorInfo, long usedTime, String createTime) {
        this.id = id;
        this.userAgent = userAgent;
        this.userIp = userIp;
        this.userId = userId;
        this.loginName = loginName;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.className = className;
        this.classDesc = classDesc;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
        this.errorInfo = errorInfo;
        this.usedTime = usedTime;
        this.createTime = createTime;
    }

    public LogInfo() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

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

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
