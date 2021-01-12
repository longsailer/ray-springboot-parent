package org.ray.excel.io.exceptions;


/**
 * OverrideMethodException.java
 * <br><br>
 * 需要强制覆盖的方法,在未覆盖时的异常
 * @author: ray
 * @date: 2019年12月24日
 */
public class OverrideMethodException extends Error {

	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 2194360744842846107L;

	public OverrideMethodException(){
		super("当前方法是需要强制覆盖的方法.");
	}
	
	public OverrideMethodException(String msg){
		super(msg);
	}
}

