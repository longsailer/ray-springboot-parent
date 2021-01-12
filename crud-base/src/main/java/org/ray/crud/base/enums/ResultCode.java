package org.ray.crud.base.enums;

/**
 * Created by TF on 2018/2/6.
 */
public enum ResultCode {
	/**
	 * 成功
	 */
    SUCCESS(200),//成功
    /**
     * 失败
     */
    FAIL(400),//失败
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),//未认证（签名错误）
    /**
     * 接口不存在
     */
    NOT_FOUND(404),//接口不存在
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
