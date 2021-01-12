package org.ray.operation.info;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-01 16:32
 * @Description:
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    private LogInfoUtils logInfoService;

    @Autowired
    private LogAbstract logAbstract;

    private LogInfo logInfo;

    private long startTime;

    /**
     * @param request
     * @param response
     * @param handler
     * @Method preHandle
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 方法执行之前
     * @Return boolean
     * @Exception
     * @Date 2019-07-11 11:04
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logInfo = new LogInfo();
        //开始时间
        startTime = System.currentTimeMillis();

        logger.info(request.getRequestURI() + " is starting!");

        //request
        logInfo.setUserAgent(request.getHeader("User-Agent"));
        logInfo.setRequestUrl(request.getRequestURI());
        logInfo.setRequestMethod(request.getMethod());

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //类
        logInfo.setClassName(handlerMethod.getBeanType().getName());

        //获取 Api 和 ApiOperation 注解的描述
        Api api = handlerMethod.getBeanType().getAnnotation(Api.class);

        if (api != null) {
            logInfo.setClassDesc(api.value());
        }

        //方法
        Method method = handlerMethod.getMethod();
        logInfo.setMethodName(method.getName());

        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
            logInfo.setMethodDesc(apiOperation.value());
        }

        //用户信息
        try {
            //获取用户信息
            UserInfoDto userInfoDto = logAbstract.getUser(request);
            if (userInfoDto != null) {
                logInfo.setUserId(userInfoDto.getUserId());
                logInfo.setLoginName(userInfoDto.getLoginName());
            } else {
                logger.info("userInfoDto is null");
            }
        } catch (Exception e) {
            logger.error("获取用户信息失败，{}", e.getMessage());
        }

        logInfo.setUserIp(logInfoService.getIp(request));

        return true;
    }

    /**
     * @param request
     * @param response
     * @param o
     * @param modelAndView
     * @Method postHandle
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 方法成功执行之后 才会执行次方法
     * @Return void
     * @Exception
     * @Date 2019-07-11 11:06
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //过滤的方法成功执行 才会执行次方法
        logger.info("postHandle");
    }

    /**
     * @param request
     * @param response
     * @param o
     * @param e
     * @Method afterCompletion
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 方法执行完成（成功/失败）之后
     * @Return void
     * @Exception
     * @Date 2019-07-11 11:06
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        try {

            //获取数据源
            JdbcTemplate jdbcTemplate = logAbstract.createJdbcTemplate();

            //过滤的方法执行之后的错误信息
            if (e != null) {
                logInfo.setErrorInfo(e.toString());
            }

            //耗时
            long usedTime = System.currentTimeMillis() - startTime;
            logger.info(request.getRequestURI() + " is end! The total used time is " + usedTime + " ms");

            logInfo.setUsedTime(usedTime);
            StringBuilder ddl = new StringBuilder("INSERT INTO `t_log_info`(`userAgent`, `userIp`, `userId` , `loginName` , `requestUrl` , `requestMethod` , `className` , `classDesc` , `methodName` , `methodDesc` , `errorInfo` , `usedTime`) VALUES");
            ddl.append("('" + logInfo.getUserAgent() + "' ,'" + logInfo.getUserIp() + "' ,'" + logInfo.getUserId() + "' ,\"" + logInfo.getLoginName() + "\",'" + logInfo.getRequestUrl() + "' ,'" + logInfo.getRequestMethod() + "' ,'" + logInfo.getClassName() + "' ,'");
            ddl.append(logInfo.getClassDesc() + "' ,'" + logInfo.getMethodName() + "' ,'" + logInfo.getMethodDesc() + "' ,'" + logInfo.getErrorInfo() + "' ,'" + logInfo.getUsedTime() + "' )");

            logger.info("t_log_info insert ddl==={}", ddl);
            jdbcTemplate.update(ddl.toString());

            logger.info("log insert success");

        } catch (Exception ex) {
            logger.error("log insert fail,{}", ex.getMessage());
        }

    }
}
