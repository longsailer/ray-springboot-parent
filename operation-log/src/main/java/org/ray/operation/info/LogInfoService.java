package org.ray.operation.info;

import java.util.List;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-10 13:55
 * @Description: 接口
 */
public interface LogInfoService {

    /**
     * @param logInfoDto
     * @param pageNumber
     * @param pageSize
     * @Method logInfoQuery
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 日志信息查询接口
     * @Return java.util.List<org.ray.nz.log.LogInfo>
     * @Exception
     * @Date 2019-07-11 10:53
     */
    List<LogInfo> logInfoQuery(LogInfoDto logInfoDto, Integer pageNumber, Integer pageSize);

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
     Long logInfoTotal(LogInfoDto logInfoDto);

}
