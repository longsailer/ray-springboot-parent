package org.ray.excel.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.ray.excel.io.enums.ExcelType;
import org.ray.excel.io.setters.ExcelCellSetter;

/**
 * ExcelExportService.java
 * <br><br>
 * 把数据导出为Excel
 * @author: ray
 * @date: 2019年12月24日
 */
public class ExcelExportHttpResponse<T> extends AbstractExcelExport<T> {

	private HttpServletResponse response;
	
	private String fileName = "excel-data.xls";
	
	private String[] headerName;
	
	private ExcelCellSetter<T> setter;
	
	public ExcelExportHttpResponse(){
		this.createExcelWorkbook(ExcelType.XLS);
	}
	
	public ExcelExportHttpResponse(HttpServletResponse response){
		this.response = response;
		this.createExcelWorkbook(ExcelType.XLS);
	}
	
	public void setFileName(String fileName){
		if(fileName != null && !"".equals(fileName)){
			this.fileName = fileName;
		}
	}
	
	public void setHeader(String[] headerName){
		this.headerName = headerName;
	}
	
	public void setExcelCellSetter(ExcelCellSetter<T> setter){
		this.setter = setter;
	}
	
	public void write(List<T> data) throws IOException{
		if(response == null) throw new IOException("The response is null.");
		OutputStream out = response.getOutputStream();
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(this.fileName, "UTF-8"));
        response.setContentType("application/octet-stream; charset=UTF-8");
        this.export(out, data);
	}

	public String[] getHeader() {
		return this.headerName;
	}

	public ExcelCellSetter<T> getSetter() {
		return this.setter;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}

