package org.ray.excel.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * FileImport.java
 * <br><br>
 * 文件导入接口
 * @author: ray
 * @date: 2019年12月23日
 */
public interface FileImport<T> {

	/**
	 * 把文件解析后，转换为数据对象
	 * @author ray
	 * @param is
	 * @return List&lt;T&gt;
	 * @throws IOException 
	 */
	public List<T> imports(InputStream is) throws IOException;
	
}

