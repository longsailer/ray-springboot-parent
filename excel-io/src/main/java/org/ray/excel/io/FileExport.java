package org.ray.excel.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * FileImport.java
 * <br><br>
 * 文件导出接口
 * @author: ray
 * @date: 2019年12月23日
 */
public interface FileExport<T> {
	
	/**
	 * 把给定的数据导出成file的二进制数据
	 * @author ray
	 * @param fileName
	 * @param data
	 * @return byte[]
	 */
	public void export(OutputStream out, List<T> data) throws IOException;
}
