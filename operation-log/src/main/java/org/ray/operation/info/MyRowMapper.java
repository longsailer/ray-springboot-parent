package org.ray.operation.info;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-01 16:32
 * @Description:
 */
class MyRowMapper implements RowMapper<LogInfo> {

    /**
     * @param rs
     * @param num
     * @Method mapRow
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description
     * @Return org.ray.nz.log.LogInfo
     * @Exception
     * @Date 2019-07-11 11:12
     */
    @Override
    public LogInfo mapRow(ResultSet rs, int num) throws SQLException {
        //从结果集里把数据得到
        long id = Long.parseLong(rs.getString("id"));
        String userAgent = rs.getString("userAgent");
        String userIp = rs.getString("userIp");
        String userId = rs.getString("userId");

        String loginName = rs.getString("loginName");
        String requestUrl = rs.getString("requestUrl");
        String requestMethod = rs.getString("requestMethod");
        String className = rs.getString("className");

        String classDesc = rs.getString("classDesc");
        String methodName = rs.getString("methodName");
        String methodDesc = rs.getString("methodDesc");
        String errorInfo = rs.getString("errorInfo");

        long usedTime = Long.parseLong(rs.getString("usedTime"));
        String createTime = rs.getString("createTime");

        //把数据封装到对象里
        return new LogInfo(id, userAgent, userIp, userId, loginName, requestUrl, requestMethod, className,
                classDesc, methodName, methodDesc, errorInfo, usedTime, createTime);
    }

}
