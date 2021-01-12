package org.ray.crud.base;

import org.ray.crud.base.enums.ResultCode;
import org.ray.crud.base.vo.Result;

/**
 * BaseCommonResult.java
 * <br><br>
 * [write note]
 * @author: ray
 * @date: 2019年12月11日
 */
public class BaseCommonResult {
	/**
	 * 成功时返回
	 * @author ray
	 * @param t
	 * @param msg
	 * @return Result<T>
	 */
	public <T> Result<T> success(T t, String msg){
    	Result<T> rr = new Result<T>();
		rr.setData(t);
		rr.setStatus(ResultCode.SUCCESS);
		rr.setMsg(msg);
		return rr;
	}
	/**
	 * 成功时返回[带分页]
	 * @author ray
	 * @param t
	 * @param totalCount
	 * @param totalPage
	 * @return Result<T>
	 */
	public <T> Result<T> success(T t, Long totalCount, Integer totalPage){
		Result<T> rr = new Result<T>();
		rr.setData(t);
		rr.setStatus(ResultCode.SUCCESS);
		rr.setCount(totalCount);
		rr.setTotalPage(totalPage);
		return rr;
	}
	/**
	 * 成功时返回[只有消息]
	 * @author ray
	 * @param msg
	 * @return Result<T>
	 */
	public <T> Result<T> success(String msg){
		Result<T> rr = new Result<T>();
		rr.setStatus(ResultCode.SUCCESS);
		rr.setMsg(msg);
		return rr;
	}
	/**
	 * 成功时返回[只有返回值，没有消息]
	 * @author ray
	 * @param t
	 * @return Result<T>
	 */
	public <T> Result<T> success(T t){
		return success(t, "");
	}
	/**
	 * 成功时返回[不带返回值，也没有消息]
	 * @author ray
	 * @return Result<String>
	 */
	public <T> Result<T> success(){
		return success(null, "");
	}
	/**
	 * 发生错误时返回
	 * @author ray
	 * @param t
	 * @param rc
	 * @param msg
	 * @return Result<T>
	 */
	public <T> Result<T> error(T t, ResultCode rc, String msg){
		Result<T> rr = new Result<T>();
		rr.setData(t);
		rr.setStatus(rc);
		rr.setMsg(msg);
		return rr;
	}
	/**
	 * 发生错误时返回[无返回值]
	 * @author ray
	 * @param rc
	 * @param msg
	 * @return Result<T>
	 */
	public <T> Result<T> error(ResultCode rc, String msg){
		Result<T> rr = new Result<T>();
		rr.setData(null);
		rr.setStatus(rc);
		rr.setMsg(msg);
		return rr;
	}
	/**
	 * 发生错误时返回[无返回值]
	 * @author ray
	 * @param rc
	 * @param msg
	 * @return Result<T>
	 */
	public <T> Result<T> error(String msg){
		Result<T> rr = new Result<T>();
		rr.setData(null);
		rr.setStatus(ResultCode.FAIL);
		rr.setMsg(msg);
		return rr;
	}
	/**
	 * 服务器端发生错误
	 * @author ray
	 * @return Result<T>
	 */
	public <T> Result<T> error(){
		return error(null, ResultCode.INTERNAL_SERVER_ERROR, "服务器端发生错误");
	}
	/**
	 * 找不到您请求的路径
	 * @author ray
	 * @return Result<T>
	 */
	public <T> Result<T> notFound(){
		return error(null, ResultCode.NOT_FOUND, "找不到您请求的路径");
	}
	/**
	 * 您无权访问
	 * @author ray
	 * @return Result<T>
	 */
	public <T> Result<T> accessDenied(){
		return error(null, ResultCode.UNAUTHORIZED, "您无权访问");
	}
}

