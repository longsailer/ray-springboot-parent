/**
 * QueryParams.java
 * @author: 杨洲
 * @date: 2018年4月13日
 */
package org.ray.crud.base.vo;

import java.io.Serializable;

public abstract class QueryParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2813746911723771738L;
	private Boolean status;

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
