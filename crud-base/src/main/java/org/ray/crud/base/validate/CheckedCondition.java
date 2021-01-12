package org.ray.crud.base.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ray.crud.base.enums.CheckedRules;

/**
 * CheckedCondition.java
 * <br><br>
 * 实体属性上设置校验的条件
 * @author: ray
 * @date: 2019年12月17日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckedCondition {
 
	/**
	 * 是否必填
	 * @author ray
	 * @return boolean
	 */
	boolean required() default false;
	/**
	 * 校验类型<br>
	 * 有手机号，邮箱地址，身份证号...等等，详细参见类{@link CheckedRules}
	 * @author ray
	 * @return CheckedRules
	 */
	CheckedRules rule() default CheckedRules.none;
	/**
	 * 最大长度不能超过该值，0表示不限制
	 * @author ray
	 * @return int
	 */
	int maxLength() default 0;
	/**
	 * 最小长度不能小于该值，0表示不限制
	 * @author ray
	 * @return int
	 */
	int minLength() default 0;
	/**
	 * 验证不通过时，错误提示信息<br>
	 * [必填]
	 * @author ray
	 * @return int
	 */
	String errorMsg();
}

