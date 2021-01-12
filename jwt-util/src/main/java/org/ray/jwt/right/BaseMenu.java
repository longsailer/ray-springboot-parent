package org.ray.jwt.right;

import java.io.Serializable;

/**
 * BaseMenu.java
 * <br><br>
 * 基础菜单类
 * @author: ray
 * @date: 2020年1月9日
 * @see 该类作用是为了保障通用性，其重点是index，它是所有菜单的序号，且一旦序号产生，它与id,url的对应关系将不可再变
 */
public class BaseMenu implements Serializable {
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = -3553536887283915949L;

	private String id;
	private String url;
	private int index;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}

