package org.ray.crud.base.validate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.ray.crud.base.BaseCommonResult;
import org.ray.crud.base.enums.CheckedRules;
import org.ray.crud.base.vo.Result;

/**
 * EntityValidationUtils.java
 * <br><br>
 * 根据实体类进行校验
 * @see
 * 目前暂仅支持针对string类型的属性进行校验，数值类型的请自行判断
 * @author: ray
 * @date: 2019年12月18日
 */
public class EntityValidationUtils {
	
	private static BaseCommonResult baseResult = new BaseCommonResult();

	/**
	 * 偏历实体对象指定需校验属性
	 * @author ray
	 * @param t
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @return Result<String>
	 */
	public static Result<String> validate(Object t) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Field[] fields = t.getClass().getDeclaredFields();
		for(Field f : fields){
			CheckedCondition checkAnno = f.getAnnotation(CheckedCondition.class);
			if(checkAnno == null){
				continue;
			}
			String methodName = "get"+f.getName().substring(0, 1).toUpperCase()+f.getName().substring(1);
			Method method = t.getClass().getMethod(methodName);
			Object value = method.invoke(t);
			
			if(value == null || value instanceof String){
				ValidateResult vr = verifyField((String)value, checkAnno);
				if(!vr.getStatus()){
					return baseResult.error(vr.getMsg());
				}
			}
		}
		return baseResult.success();
	}
	/**
	 * 针对单个属性值，按指定的规则进行校验
	 * @author ray
	 * @param value
	 * @param checkAnno
	 * @return ValidateResult
	 */
	private static ValidateResult verifyField(String value, CheckedCondition checkAnno){
		ValidateResult vr = new ValidateResult();
		boolean isRequired = checkAnno.required();
		if(isRequired && (value == null || "".equals(value))){
			vr.setStatus(false);
			vr.setMsg(checkAnno.errorMsg());
			return vr;
		}
		
		if(value != null && !"".equals(value)){
			
			if(!checkAnno.rule().equals(CheckedRules.none) && !checkAnno.rule().test(value)){
				vr.setStatus(false);
				vr.setMsg(checkAnno.errorMsg());
				return vr;
			}
			
			if(checkAnno.maxLength() > 0 && value.length() > checkAnno.maxLength()){
				vr.setStatus(false);
				vr.setMsg(checkAnno.errorMsg());
				return vr;
			}
			
			if(checkAnno.minLength() > 0 && value.length() < checkAnno.minLength()){
				vr.setStatus(false);
				vr.setMsg(checkAnno.errorMsg());
				return vr;
			}
			
		}
		
		vr.setStatus(true);
		return vr;
	}
	
	private static class ValidateResult {
		private boolean status;
		private String msg;
		public boolean getStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}

