/**
 * JsonMapper.java
 * @author: 杨洲
 * @date: 2018年5月18日
 */
package org.ray.jwt.json;

public interface JsonMapper<T> {

	public String objectToJson(T t);
	
	public T jsonToObject(String json);
}
