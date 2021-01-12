package org.ray.excel.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.ray.excel.io.enums.ExcelType;

/**
 * ExcelWorkbook.java <br>
 * <br>
 * Excel文档对象，可以进行创建sheet，并写入数据
 * @author: ray
 * @date: 2019年12月23日
 */
public final class ExcelWorkbook {

	private Workbook wb;
	
	
	public ExcelWorkbook(ExcelType type){
		if (ExcelType.XLS.equals(type)) {
			this.wb = new HSSFWorkbook();
		} else if (ExcelType.XLSX.equals(type)) {
			this.wb = new XSSFWorkbook();
		} else { // 默认为2003版本
			this.wb = new HSSFWorkbook();
		}
	}
	
	public ExcelWorkbook(InputStream is) throws IOException{
		this(is, ExcelType.XLS);
	}
	
	public ExcelWorkbook(InputStream is, ExcelType type) throws IOException{
		if (ExcelType.XLS.equals(type)) {
			this.wb = new HSSFWorkbook(is);
		} else if (ExcelType.XLSX.equals(type)) {
			this.wb = new XSSFWorkbook(is);
		} else { // 默认为2003版本
			this.wb = new HSSFWorkbook(is);
		}
	}

	public <T> ExcelWorkSheet<T> createWorkSheet() {
		return new ExcelWorkSheet<T>(wb);
	}
	
	public <T> ExcelWorkSheet<T> createWorkSheet(String sheetName) {
		return new ExcelWorkSheet<T>(wb, sheetName);
	}
	
	public <T> ExcelWorkSheet<T> createWorkSheet(List<T> data) {
		return new ExcelWorkSheet<T>(wb, data);
	}
	
	public <T> ExcelWorkSheet<T> createWorkSheet(String[] headerName, List<T> data) {
		return new ExcelWorkSheet<T>(wb, headerName, data);
	}
	
	public <T> ExcelWorkSheet<T> createWorkSheet(String sheetName, String[] headerName, List<T> data) {
		return new ExcelWorkSheet<T>(wb, sheetName, headerName, data);
	}
	
	public <T> ExcelWorkSheet<T> getWorkSheet() {
		return new ExcelWorkSheet<T>(wb.getSheetAt(0));
	}
	
	public <T> ExcelWorkSheet<T> getWorkSheet(int skipRows) {
		return new ExcelWorkSheet<T>(wb.getSheetAt(0), skipRows);
	}
	
	public <T> ExcelWorkSheet<T> getWorkSheet(int sheetIndex, int skipRows) {
		return new ExcelWorkSheet<T>(wb.getSheetAt(sheetIndex), skipRows);
	}
	
	public <T> ExcelWorkSheet<T> getWorkSheet(int sheetIndex, boolean isHeader, int skipRows) {
		return new ExcelWorkSheet<T>(wb.getSheetAt(sheetIndex), isHeader, skipRows);
	}
	
	/**
	 * 把数据写到流中
	 * @author ray
	 * @param stream
	 * @throws IOException
	 * @return void
	 */
	public void write(OutputStream stream) throws IOException{
		this.wb.write(stream);
	}
	/**
	 * 关闭当前文件写通道
	 * @author ray
	 * @throws IOException
	 * @return void
	 */
	public void close() throws IOException{
		this.wb.close();
	}
}
