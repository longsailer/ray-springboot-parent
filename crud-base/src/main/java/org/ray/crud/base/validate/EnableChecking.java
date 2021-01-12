package org.ray.crud.base.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import org.ray.crud.base.vo.BaseEntity;

/**
 * EnableChecking.java
 * <br><br>
 * 开启自动实体校验功能
 * @see
 * {@code value & argIndex } 都是为了指定需校验的参数位置，第一个参数位置为0，依次类推
 * @author: ray
 * @date: 2019年12月18日
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableChecking {
	/**
	 * 指定要校验的实体对象是第几个参数
	 * 第一个参数位置为0，默认，可以不设置
	 * @author ray
	 * @return int
	 */
	@AliasFor("argIndex")
	int value() default 0;
	/**
	 * 指定要校验的实体对象是第几个参数
	 * 第一个参数位置为0，默认，可以不设置
	 * @author ray
	 * @return int
	 */
	@AliasFor("value")
	int argIndex() default 0;
	/**
	 * 指定要校验的实体对象的类
	 * @author ray
	 * @return Class<? extends BaseEntity>
	 */
	Class<?> argClass() default BaseEntity.class;
}

