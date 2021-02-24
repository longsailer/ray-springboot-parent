package org.ray.crud.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * BaseController.java
 * <br><br>
 * 不包含增删改查的基础Controller类
 * @author: ray
 * @date: 2019年12月11日
 */
public class BaseController extends BaseCommonResult{
    
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	protected String getUserId() {
		return (String)getRequest().getAttribute("userId");
	}
	
	protected String getReqHost() {
        HttpServletRequest request = getRequest();
        String fh = request.getHeader("x-forwarded-for");
        if(fh != null && fh.indexOf(",") != -1){
        	fh = fh.split(",")[0];
        }
        return fh==null?request.getRemoteHost():fh;
    }

    protected String getReqAgent() {
        HttpServletRequest request = getRequest();
        return request.getHeader("User-Agent");
    }

    protected String getReqUrl() {
        HttpServletRequest request = getRequest();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        return path;
    }
}

