package org.ray.crud.base.exceptions;


/**
 * ArgClassCastException.java
 * <br><br>
 * [write note]
 * @author: ray
 * @date: 2019年12月18日
 */
public class ArgClassCastException extends Exception {

	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = -3405623476706464074L;
	
	public ArgClassCastException(){
		super("参数类型不匹配");
	}
	
	public ArgClassCastException(String msg){
		super(msg);
	}
}

