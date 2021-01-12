package org.ray.excel.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import org.ray.excel.io.enums.ExcelType;
import org.ray.excel.io.setters.ExcelCellGetter;

/**
 * ExcelImportHttpRequest.java
 * <br><br>
 * 根据文件流，读取excel中的数据，默认格式是xls，读取第1个sheet中的内容
 * @author: ray
 * @date: 2020年1月2日
 */
public class ExcelImportHttpRequest<T> extends AbstractExcelImport<T>{
	
	private ExcelCellGetter<T> excelCellGetter;
	
	private MultipartHttpServletRequest request;
	
	private MultipartFile file;
	
	public ExcelImportHttpRequest(){
	}
	
	public ExcelImportHttpRequest(HttpServletRequest request, String parameterName){
		this.request = new DefaultMultipartHttpServletRequest(request);
		this.file = this.request.getFiles(parameterName).get(0);
	}
	
	public ExcelImportHttpRequest(MultipartFile file){
		this.file = file;
	}

	public ExcelCellGetter<T> getExcelCellGetter() {
		return excelCellGetter;
	}

	public ExcelImportHttpRequest<T> setExcelCellGetter(ExcelCellGetter<T> excelCellGetter) {
		this.excelCellGetter = excelCellGetter;
		return this;
	}
	
	public ExcelImportHttpRequest<T> setExcelType(ExcelType type) {
		this.excelType = type;
		return this;
	}
	
	public ExcelImportHttpRequest<T> skipRows(int rowNum) {
		this.skipRows = rowNum;
		return this;
	}
	
	/**
	 * 读取导入的文件转换为数据集合
	 * @see 前提是使用于构造函数初始化<code>MultipartFile</code>
	 * @author ray
	 * @throws IOException
	 * @return List<T>
	 */
	public List<T> read() throws IOException{
		try {
			if(file == null) throw new IOException("没有接到任何文件导入请求");
			return this.read(file.getInputStream());
		} catch (IOException e) {
			throw new IOException("请注意检查文件格式，或解析时发生错误.");
		}finally{
			this.close();
		}
	}
	
	/**
	 * 根据文件流进行解析，转换为数据集合
	 * @author ray
	 * @param is
	 * @throws IOException
	 * @return List<T>
	 */
	public List<T> read(InputStream is) throws IOException{
		try {
			List<T> data = this.imports(is);
			return data;
		} catch (IOException e) {
			throw new IOException("请注意检查文件格式，或解析时发生错误.");
		}finally{
			this.close();
		}
	}
}

