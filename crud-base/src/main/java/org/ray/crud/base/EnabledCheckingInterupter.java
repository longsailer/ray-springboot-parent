package org.ray.crud.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.ray.crud.base.exceptions.ArgClassCastException;
import org.ray.crud.base.validate.EnableChecking;
import org.ray.crud.base.validate.EntityValidationUtils;
import org.ray.crud.base.vo.Result;

/**
 * EnabledCheckingInterupter.java
 * <br><br>
 * 处理需要进行实体校验的Controller层的方法，并进行自动化校验
 * @author: ray
 * @date: 2019年12月18日
 */
@Aspect
@Component
public class EnabledCheckingInterupter {
	
	private Logger log = LoggerFactory.getLogger(EnabledCheckingInterupter.class);

	@Pointcut("execution(public * org.ray..controller.*.*(..)) && @annotation(org.ray.crud.base.validate.EnableChecking)")
	public void enableChecking(){}
	
	@Around("enableChecking()")
	public Object processChecking(ProceedingJoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		MethodSignature s = (MethodSignature)joinPoint.getSignature();
		BaseCommonResult proxyResult = new BaseCommonResult();
		EnableChecking ec = s.getMethod().getAnnotation(EnableChecking.class);
		int argIndex = ec.argIndex() < ec.value() ? ec.value():ec.argIndex();
		Class<?> argClass = ec.argClass();
		if(args[argIndex] == null || argClass != null && !args[argIndex].getClass().equals(argClass) && !args[argIndex].getClass().getGenericSuperclass().equals(argClass)){
			String errorMsg = String.format("自动化校验时，发现设置的参数[%s]与指定的类型[%s]不匹配", args[argIndex], argClass);
			log.error(errorMsg, new ArgClassCastException());
			return proxyResult.error("自动化校验时，发生错误");
		}
		try{
			Result<String> validateResult = EntityValidationUtils.validate(args[argIndex]);
			if(validateResult.getStatus() != 200){
				return validateResult;
			}
			return joinPoint.proceed();
		}catch(Throwable e){
			log.error("自动化校验时，发生错误", e);
			return proxyResult.error("自动化校验时，发生错误");
		}
	}
}
