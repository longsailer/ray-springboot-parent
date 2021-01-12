package org.ray.excel.io;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.ray.excel.io.setters.ExcelCellGetter;
import org.ray.excel.io.setters.ExcelCellSetter;

/**
 * ExcelWorkSheet.java
 * <br><br>
 * Excel操作的sheet对象，把数据写入sheet
 * @author: ray
 * @date: 2019年12月23日
 */
public class ExcelWorkSheet<T> {
	private String[] headerName;
	private List<T> data;
	private String sheetName;
	
	private boolean isHeader = false;
	private int skipRows = 0;
	
	private ExcelCellSetter<T> excelCellSetter;
	private ExcelCellGetter<T> excelCellGetter;
	
	private Sheet sheet;
	
	public ExcelWorkSheet(Workbook wb){
		this.sheet = wb.createSheet();
		this.sheetName = this.sheet.getSheetName();
	}
	
	public ExcelWorkSheet(Workbook wb, String sheetName){
		if(sheetName != null && !"".equals(sheetName))
			this.sheet = wb.createSheet(sheetName);
		else
			this.sheet = wb.createSheet();
		
		this.sheetName = this.sheet.getSheetName();
	}
	
	public ExcelWorkSheet(Workbook wb, String sheetName, String[] headerName){
		this(wb, sheetName);
		this.headerName = headerName;
		this.isHeader = headerName != null && headerName.length > 0;
	}
	
	public ExcelWorkSheet(Workbook wb, String[] headerName, List<T> data){
		this(wb);
		this.headerName = headerName;
		this.data = data;
		this.isHeader = headerName != null && headerName.length > 0;
	}
	
	public ExcelWorkSheet(Workbook wb, List<T> data){
		this(wb);
		this.data = data;
	}
	
	public ExcelWorkSheet(Workbook wb, String sheetName, String[] headerName, List<T> data){
		this(wb, sheetName);
		this.sheetName = sheetName;
		this.headerName = headerName;
		this.data = data;
		this.isHeader = headerName != null && headerName.length > 0;
	}
	
	
	public ExcelWorkSheet(Sheet sheet){
		this(sheet, false, 0);
	}
	
	public ExcelWorkSheet(Sheet sheet, int skipRows){
		this(sheet, false, skipRows);
	}
	
	public ExcelWorkSheet(Sheet sheet, boolean isHeader, int skipRows){
		this.sheet = sheet;
		this.sheetName = sheet.getSheetName();
		this.isHeader = isHeader;
		this.skipRows = skipRows;
	}
	
	public List<T> getData(){
		int firstRowIndex = this.sheet.getFirstRowNum();
		if(this.isHeader) firstRowIndex++;
		if(this.skipRows > 0) firstRowIndex = skipRows;
		int lastRowIndex = this.sheet.getLastRowNum();
		
		List<T> dataList = new ArrayList<T>();
		for(int rowNum = firstRowIndex; rowNum < lastRowIndex; rowNum++){
			Row row = this.sheet.getRow(rowNum);
			short firstColNum = row.getFirstCellNum();
			short lastColNum = row.getLastCellNum();
			String[] rowData = new String[lastColNum-firstColNum+1];
			for (int colNum = firstColNum; colNum <= lastColNum; colNum++) {
				Cell cell = row.getCell(colNum);
				rowData[colNum-firstColNum] = cell.getRichStringCellValue().getString();
			}
			dataList.add(excelCellGetter.mapping(rowData));
		}
		return dataList;
	}
	
	public ExcelWorkSheet<T> setData(List<T> data){
		this.data = data;
		return this;
	}
	
	public ExcelWorkSheet<T> setHeader(String[] headerName){
		this.headerName = headerName;
		this.isHeader = headerName != null && headerName.length > 0;
		return this;
	}
	
	public ExcelWorkSheet<T> setSetter(ExcelCellSetter<T> setter){
		this.excelCellSetter = setter;
		return this;
	}
	
	public ExcelWorkSheet<T> setGetter(ExcelCellGetter<T> getter){
		this.excelCellGetter = getter;
		return this;
	}
	
	public String getSheetName(){
		return this.sheetName;
	}
	
	public void save() throws NullPointerException{
		if(data == null) throw new NullPointerException("无数据可以保存到当前Sheet");
		this.createHeader();
		this.createBody(excelCellSetter);
	}
	/**
	 * 创建表头
	 * @author ray
	 * @return void
	 */
	private void createHeader(){
		if(!isHeader) return;
		Row row = sheet.createRow(0);
		for(int i=0; i<headerName.length; i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(headerName[i]);
		}
	}
	/**
	 * 按实体整行写入
	 * @see
	 * 请按要求实现抽象类{@link ExcelCellSetter}，把实体的属性与行中的行对应起来<br>
	 * 实例：
	 * <pre>
	 * new ExcelCellSetter&lt;Enterprises&gt;{
	 * 	public String[] mapping(Enterprises t) {
	 *		return new String[]{
	 *			t.getId(), t.getEpName()
	 *		};
	 *	}
	 * }
	 * </pre>
	 * @author ray
	 * @param setter
	 * @return void
	 */
	private void createBody(ExcelCellSetter<T> setter){
		int firstRowIndex = isHeader ? 1 : 0;
		for(int i = 0; i < this.data.size(); i++){
			T t = data.get(i);
			Row row = this.sheet.createRow(firstRowIndex + i);
			String[] rowData = setter.mapping(t);
			for(int col = 0; col < rowData.length; col++){
				Cell cell = row.createCell(col);
				cell.setCellValue(rowData[col]);
			}
		}
	}
}

