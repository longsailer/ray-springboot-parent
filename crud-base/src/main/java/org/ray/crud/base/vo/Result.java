package org.ray.crud.base.vo;

import java.io.Serializable;

import org.ray.crud.base.enums.ResultCode;

/**
 * 	返回结果
 */
public class Result<T> implements Serializable{
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 8855695365166314860L;
	private int status;
	private long count;
	private long totalPage;
	private String msg;
	private T data;

	public Result<T> setStatus(ResultCode resultCode) {
		this.status = resultCode.code;
		return this;
	}

	public Result<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}


	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}
	/**
	 * @return the totalCount
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the totalPage
	 */
	public long getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
}
