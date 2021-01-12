package org.ray.crud.base.enums;

/**
 * CheckedRules.java
 * <br><br>
 * 校验规则
 * <br>
 * 目前仅收编包含：手机号、邮箱地址、身份证号、域名、网址、固定电话、IP地址、邮政编码。
 * 后面可以再持续增加
 * @author: ray
 * @date: 2019年12月17日
 */
public enum CheckedRules {

	/**
	 * 手机号
	 */
	mobile {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|19[0|1|2|3|5|6|7|8|9])\\d{8}$");
		}
	},
	/**
	 * 邮箱地址
	 */
	email {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		}
	},
	/**
	 * 身份证号
	 */
	id {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^\\d{17}\\d|x$");
		}
	},
	/**
	 * 域名
	 */
	domain {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("[a-zA-Z0-9]{0,62}\\.{0,1}[a-zA-Z0-9]{0,62}\\.{0,1}[a-z]{2,3}");
		}
	},
	/**
	 * 网址
	 */
	url {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("http[s]{0,1}://([\\w-]+\\.)+[\\w-]+(/{0,1}[\\w-./?%&=]*)?$");
		}
	},
	/**
	 * 固定电话
	 */
	telephone {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^\\d{3,4}-\\d{7,8}$");
		}
	},
	/**
	 * IP地址
	 */
	ip {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$");
		}
	},
	/**
	 * 邮政编码
	 */
	postcode {
		public boolean test(String v){
			if(v == null || "".equals(v)){
				return false;
			}
			return v.matches("^\\d{6}$");
		}
	},
	/**
	 * 无校验规则
	 */
	none {
		public boolean test(Object v){
			return true;
		}
	};
	
	public boolean test(Object v){
		throw new AbstractMethodError();
	}
	
	public boolean test(String v){
		throw new AbstractMethodError();
	}
}

