package org.ray.excel.io.setters;

/**
 * ExcelCellGetter.java
 * <br><br>
 * 把一个从Excel中读取出来的字符数组，转换为实体
 * @author: ray
 * @date: 2020年1月2日
 * @see
 * 例如：
 * <pre>
 * new ExcelCellGetter<Enterprises>() {
 *		public Enterprises mapping(String[] row) {
 *			Enterprises e = new Enterprises();
 *			e.setId(row[0]);
 *			e.setEpName(row[1]);
 *			return e;
 *		}
 *	}
 * </pre>
 */
public abstract class ExcelCellGetter<T> {
	public abstract T mapping(String[] row);
}

