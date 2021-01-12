package org.ray.crud.base.enums;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

/**
 * EntityFieldDataType.java
 * <br><br>
 * 实体属性的类型
 * @author: ray
 * @date: 2019年12月17日
 */
public enum EntityFieldDataType {

	numberic {
		public <T> boolean validate(T t){
			if(t instanceof Integer || t instanceof Float || t instanceof Long || t instanceof Double || t instanceof BigDecimal){
				return true;
			}
			return false;
		}
	},
	
	string {
		public <T> boolean validate(T t){
			if(t instanceof String){
				return true;
			}
			return false;
		}
	},
	
	date {
		public <T> boolean validate(T t){
			if(t instanceof Date || t instanceof Timestamp){
				return true;
			}
			return false;
		}
	};
	
	public <T> boolean validate(T t){
		throw new AbstractMethodError();
	}
	
	public static <T> EntityFieldDataType dataType(T t){
		if(t instanceof Integer || t instanceof Float || t instanceof Long || t instanceof Double || t instanceof BigDecimal){
			return numberic;
		}else if(t instanceof String){
			return string;
		}else if(t instanceof Date || t instanceof Timestamp){
			return date;
		}
		return null;
	}
}

