package org.ray.excel.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.ray.excel.io.enums.ExcelType;
import org.ray.excel.io.setters.ExcelCellGetter;

/**
 * AbstractExcelImport.java <br>
 * <br>
 * Excel导入，通过对POI对象的封装实现
 * @author: ray
 * @date: 2020年1月2日
 */
public abstract class AbstractExcelImport<T> implements FileImport<T> {
	
	private ExcelWorkbook ew;
	
	protected ExcelType excelType = ExcelType.XLS;
	protected int skipRows = 0;

	public List<T> imports(InputStream is) throws IOException {
		this.ew = new ExcelWorkbook(is, excelType);
		ExcelWorkSheet<T> sheet = this.ew.getWorkSheet(skipRows);
		sheet.setGetter(getExcelCellGetter());
		return sheet.getData();
	}
	
	public abstract ExcelCellGetter<T> getExcelCellGetter();
	
	public void close() throws IOException{
		this.ew.close();
	}
}
