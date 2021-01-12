package org.ray.excel.io.setters;

/**
 * ExcelCellSetter.java
 * <br><br>
 * 把一个实体，转换为字符串数组
 * @author: ray
 * @date: 2019年12月23日
 */
public abstract class ExcelCellSetter<T> {
	public abstract String[] mapping(T t);
}

