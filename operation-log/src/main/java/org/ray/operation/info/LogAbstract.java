package org.ray.operation.info;


import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-05 15:27
 * @Description: 用户信息和数据 抽象类
 */
public abstract class LogAbstract {


    /**
     * @param request
     * @Method getUser
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 用户信息
     * @Return org.ray.nz.log.UserInfoDto
     * @Exception
     * @Date 2019-07-11 10:39
     */
    public abstract UserInfoDto getUser(HttpServletRequest request);


    /**
     * @param
     * @Method createJdbcTemplate
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 数据源
     * @Return org.springframework.jdbc.core.JdbcTemplate
     * @Exception
     * @Date 2019-07-11 10:39
     */
    public abstract JdbcTemplate createJdbcTemplate();

}
