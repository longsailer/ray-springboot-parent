/**
 * CustomAccessDecisionManager.java
 * @author: 杨洲
 * @date: 2018年5月23日
 */
package org.ray.jwt.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import org.ray.jwt.right.MenuRightFactory;
import org.ray.jwt.right.MenuRightUtil;


@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
	
	private MenuRightUtil userRightService;
	@Value("${spring.application.name}")
	private String systemName;
	
	public boolean supports(Class<?> clazz) {
		return true;
	}
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation fi = (FilterInvocation) object;
		HttpServletRequest request = fi.getHttpRequest();
		Long userRight = (Long)request.getAttribute("userRight");
		for(ConfigAttribute ca : configAttributes){
			Menus m = (Menus)ca;
			userRightService = MenuRightFactory.createMenuRightUtil(systemName);
			boolean isRight = userRightService.isRight(userRight, m.getUrl());
			if(!isRight){
				throw new AccessDeniedException("对不起，无权访问");
			}
		}
	}
}
