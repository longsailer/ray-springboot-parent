package org.ray.operation.info;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-10 13:56
 * @Description: 实现
 */
@Service
public class LogInfoServiceImpl implements LogInfoService {

    private static Logger logger = LoggerFactory.getLogger(LogInfoService.class);

    @Autowired
    private LogAbstract logAbstract;


    /**
     * @param logInfoDto
     * @param pageNumber
     * @param pageSize
     * @Method logInfoQuery
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 日志信息查询
     * @Return java.util.List<org.ray.nz.log.LogInfo>
     * @Exception
     * @Date 2019-07-11 10:53
     */
    @Override
    public List<LogInfo> logInfoQuery(LogInfoDto logInfoDto, Integer pageNumber, Integer pageSize) {

        try {

            StringBuilder ddl = new StringBuilder("SELECT * from t_log_info where 1=1");

            if (pageNumber == null) {
                pageNumber = 1;
            }

            if (pageSize == null) {
                pageSize = 10;
            }

            if (StringUtils.isNotBlank(logInfoDto.getUserId())) {
                ddl.append(" AND userId LIKE '%" + logInfoDto.getUserId() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getLoginName())) {
                ddl.append(" AND loginName LIKE '%" + logInfoDto.getLoginName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getRequestUrl())) {
                ddl.append(" AND requestUrl LIKE '%" + logInfoDto.getRequestUrl() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getClassName())) {
                ddl.append(" AND className LIKE '%" + logInfoDto.getClassName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getMethodName())) {
                ddl.append(" AND methodName LIKE '%" + logInfoDto.getMethodName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getErrorInfo())) {
                ddl.append(" AND errorInfo LIKE '%" + logInfoDto.getErrorInfo() + "%'");
            }

            ddl.append(" LIMIT " + (pageNumber - 1) * pageSize + "," + pageSize);

            logger.info("ddl=======" + ddl.toString());

            //获取数据源
            JdbcTemplate jdbcTemplate = logAbstract.createJdbcTemplate();

            //查询
            List<LogInfo> logInfoList = jdbcTemplate.query(ddl.toString(), new MyRowMapper());

            return logInfoList;
        } catch (Exception e) {
            logger.error("日志信息查询失败,{}", e.getMessage());
        }


        return null;
    }

    /**
     * @param logInfoDto
     * @Method logInfoTotal
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 日志信息total
     * @Return java.util.List<org.ray.nz.log.LogInfo>
     * @Exception
     * @Date 2019-07-11 10:53
     */
    @Override
    public Long logInfoTotal(LogInfoDto logInfoDto) {

        try {

            StringBuilder ddl = new StringBuilder("SELECT count(1) from t_log_info where 1=1");

            if (StringUtils.isNotBlank(logInfoDto.getUserId())) {
                ddl.append(" AND userId LIKE '%" + logInfoDto.getUserId() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getLoginName())) {
                ddl.append(" AND loginName LIKE '%" + logInfoDto.getLoginName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getRequestUrl())) {
                ddl.append(" AND requestUrl LIKE '%" + logInfoDto.getRequestUrl() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getClassName())) {
                ddl.append(" AND className LIKE '%" + logInfoDto.getClassName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getMethodName())) {
                ddl.append(" AND methodName LIKE '%" + logInfoDto.getMethodName() + "%'");
            }

            if (StringUtils.isNotBlank(logInfoDto.getErrorInfo())) {
                ddl.append(" AND errorInfo LIKE '%" + logInfoDto.getErrorInfo() + "%'");
            }

            logger.info("ddl=======" + ddl.toString());

            //获取数据源
            JdbcTemplate jdbcTemplate = logAbstract.createJdbcTemplate();

            //查询
            Long total = jdbcTemplate.queryForObject(ddl.toString(), Long.class);

            return total;
        } catch (Exception e) {
            logger.error("日志信息查询失败,{}", e.getMessage());
        }


        return null;
    }

}




