/**

 * JsonCertificateTokenFilter.java
 * @author: 杨洲
 * @date: 2018年5月21日
 */
package org.ray.jwt;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ray.jwt.token.JsonWebTokenFactory;
import org.ray.jwt.token.JsonWebTokenHandler;
/**
 * Token验证过滤器
 * @see 可以通过构造函数设置header中的参数名，默认的参数名[loginUserId, authToken]
 * @author ray
 */
public abstract class AbstractJsonWebTokenFilter implements Filter{

	private Logger logger = LoggerFactory.getLogger(AbstractJsonWebTokenFilter.class);
	
	private String userHeaderName = "loginUserId";
	private String tokenHeaderName = "authToken";
	
	private JsonWebTokenHandler jsonWebTokenHandler;
	
	public AbstractJsonWebTokenFilter(String userHeaderName, String tokenHeaderName){
		if(userHeaderName != null && !"".equals(userHeaderName))
			this.userHeaderName = userHeaderName;
		if(tokenHeaderName != null && !"".equals(tokenHeaderName))
			this.tokenHeaderName = tokenHeaderName;
		
		jsonWebTokenHandler = this.jwtHandler();
	}
	
	public void init(FilterConfig filterConfig) throws ServletException{
	}
	
	public abstract boolean preDoFilter(ServletRequest request, ServletResponse response, FilterChain chain);
	
	public abstract JsonWebTokenHandler jwtHandler();
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
    	if(!preDoFilter(request, response, chain)){
    		return;
    	}
    	
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("进行权限验证");
    	//pc验证
    	String authToken = req.getHeader(tokenHeaderName);
        String loginUserId = req.getHeader(userHeaderName);
        if(loginUserId == null || "".equals(loginUserId)){
        	loginUserId = req.getParameter(userHeaderName);
        }
        if(authToken == null || "".equals(authToken)){
        	authToken = req.getParameter(tokenHeaderName);
        }
        
        if(authToken == null || "".equals(authToken)){
        	//都不满足则无权限访问
            request.getRequestDispatcher("/error/accessDenied").forward(request, response);
            return;
        }
        
        String remoteIp = getReqHost(req);
        String userAgent = getReqAgent(req);
        logger.info(String.format("loginUserId:%s, authToken:%s, remoteIp:%s, userAgent:%s", loginUserId, authToken, remoteIp, userAgent));
        
        boolean isRight = jsonWebTokenHandler.validateToken(loginUserId, authToken, remoteIp, userAgent);
        if(isRight){
        	chain.doFilter(request, response);
        	return;
        }
        //都不满足则无权限访问
        request.getRequestDispatcher("/error/accessDenied").forward(request, response);
    }
	
	protected String getReqHost(HttpServletRequest request) {
        String fh = request.getHeader("x-forwarded-for");
        return fh==null?request.getRemoteHost():fh;
    }

    protected String getReqAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
    
    public void destroy(){
    }
}
