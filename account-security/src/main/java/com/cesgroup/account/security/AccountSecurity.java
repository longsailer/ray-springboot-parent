package org.ray.account.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-02 11:56
 * @Description: 账号安全校验
 */
@Component
public class AccountSecurity {

    private static Logger logger = LoggerFactory.getLogger(AccountSecurity.class);

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AccountSecurityConfigProperties accountSecurityConfigProperties;
    
    public AccountSecurity(){
    }

    /**
     * @param userName
     * @Method checkAccount
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 用户在登录时先校验帐户是否被锁定，在此之前保证该用户没有在其它地方已经登录。
     * @Return boolean
     * @Exception
     * @Date 2019-07-02 17:56
     */
    public boolean checkAccount(String userName) {
        try {
        	AcountCheckInfo loginInfo = redisUtils.getInRedis(accountSecurityConfigProperties.getAsKeyPrefix() + ":" + userName, AcountCheckInfo.class);
            logger.info("info========{}", loginInfo);
            if (loginInfo == null) {
                //如果没有就 set 对象
                loginInfo = new AcountCheckInfo();
                loginInfo.setUserName(userName);
                loginInfo.setRemainCount(accountSecurityConfigProperties.getAsLoginFailCount());
                loginInfo.setCreateTime(new Date().getTime());
                redisUtils.setInRedis(accountSecurityConfigProperties.getAsKeyPrefix() + ":"  + userName, loginInfo, accountSecurityConfigProperties.getAsLockTime(), TimeUnit.HOURS);
            } else if (loginInfo.getRemainCount() <= 0){
                //如果剩余重试次数 <= 0
                return false;
            }
        } catch (Exception ex) {
            logger.error("检查帐户是否锁定时发生错误", ex);
            return false;
        }
        return true;
    }


    /**
     * @param userName
     * @Method loginSuccessCallback
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 成功
     * @Return void
     * @Exception
     * @Date 2019-07-02 17:58
     */
    public void loginSuccessCallback(String userName) {
        try {
            redisUtils.deleteInRedis(accountSecurityConfigProperties.getAsKeyPrefix() + ":"  + userName);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    /**
     * @param userName
     * @Method loginFailureCallback
     * @Author wenzhang.fu
     * @Version 1.0
     * @Description 失败
     * @Return Integer 返回剩余次数
     * @Exception
     * @Date 2019-07-02 17:58
     */
    public Integer loginFailureCallback(String userName) {
        try {
        	AcountCheckInfo info = redisUtils.getInRedis(accountSecurityConfigProperties.getAsKeyPrefix() + ":"  + userName, AcountCheckInfo.class);
            logger.info("info========{}", info);
            if (info != null) {
                int remainCounts = info.getRemainCount() - 1;
                info.setRemainCount(remainCounts);
                redisUtils.setInRedis(accountSecurityConfigProperties.getAsKeyPrefix() + ":"  + userName, info, accountSecurityConfigProperties.getAsLockTime(), TimeUnit.HOURS);
                return remainCounts;
            }
            return 0;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return -1;
        }
    }
}
