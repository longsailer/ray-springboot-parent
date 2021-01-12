package org.ray.excel.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.ray.excel.io.enums.ExcelType;
import org.ray.excel.io.setters.ExcelCellSetter;

/**
 * AbstractExcelExport.java
 * <br><br>
 * 数据导出为Excel抽象类，为持续的封装提供基础支持
 * @author: ray
 * @date: 2019年12月23日
 */
public abstract class AbstractExcelExport<T> implements FileExport<T> {
	
	private ExcelWorkbook ew;
	
	public void createExcelWorkbook(ExcelType type){
		if(ew == null)
			ew = new ExcelWorkbook(type);
	}
	
	public void export(OutputStream out, List<T> data) throws IOException {
		ExcelWorkSheet<T> sheet = ew.createWorkSheet(data);
		sheet.setHeader(getHeader()).setSetter(getSetter()).save();
		ew.write(out);
		ew.close();
	}
	
	/**
	 * 设置表头
	 * @author ray
	 * @return String[]
	 */
	public abstract String[] getHeader();
	/**
	 * 设置实体与行的转换
	 * @author ray
	 * @return ExcelCellSetter<T>
	 */
	public abstract ExcelCellSetter<T> getSetter();
}

