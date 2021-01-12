package org.ray.wx.exceptions;

/**
 * 业务异常
 * Created by TF on 2018/2/6.
 */
public class WxServiceException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4853257434364989726L;

	public WxServiceException(String message){
        super(message);
    }

}
