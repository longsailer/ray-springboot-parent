/**
 * UrlFilterInvocationSecurityMetadataSource.java
 * @author: 杨洲
 * @date: 2018年5月4日
 */
package org.ray.jwt.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import org.ray.jwt.right.MenuRightFactory;
import org.ray.jwt.right.MenuRightUtil;


@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Value("${spring.application.name}")
	private String systemName;
	
	private MenuRightUtil menusRightCreativeService;
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) o;
		HttpServletRequest request = fi.getHttpRequest();
		
		String accessUrl = request.getHeader("accessUrl");
		if(StringUtils.isEmpty(accessUrl)){
			accessUrl = fi.getRequestUrl();
		}
		menusRightCreativeService = MenuRightFactory.createMenuRightUtil(systemName);
		Menus m = menusRightCreativeService.getMenu(accessUrl);
		Collection<ConfigAttribute> auths = new ArrayList<ConfigAttribute>();
		if(m != null){
			auths.add(m);
		}
		return auths;
	}

	public boolean supports(Class<?> sClass) {
		return FilterInvocation.class.isAssignableFrom(sClass);
	}
}
