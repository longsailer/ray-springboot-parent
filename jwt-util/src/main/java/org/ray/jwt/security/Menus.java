/**
 * Menus.java
 * @author: 杨洲
 * @date: 2018年5月21日
 */
package org.ray.jwt.security;

import org.springframework.security.access.ConfigAttribute;

import org.ray.jwt.right.BaseMenu;

/**
 * 菜单的父类，由两个关键字段组成
 * @see Menus
 * @author 杨洲
 */
public class Menus extends BaseMenu implements ConfigAttribute{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String attribute;
	
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
