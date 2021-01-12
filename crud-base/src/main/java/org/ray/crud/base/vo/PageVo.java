package org.ray.crud.base.vo;

import java.util.List;

import org.ray.crud.base.enums.ResultCode;
import com.github.pagehelper.PageInfo;

/**
 * PageVo.java
 * <br><br>
 * 对Pagehelper的PageInfo进行封装，避免对外直接依赖pagehelper包的对象
 * @author: ray
 * @date: 2019年12月19日
 */
public class PageVo<T> extends PageInfo<T> {

	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = -6395149235381342385L;
	
	public PageVo(){
		super();
	}
	
	public PageVo(List<T> list){
		super(list);
	}
	
	/**
	 * 在Controller层，转化为Result进行统一返回。<br>
	 * @see
	 * 详细的情况可以了解类{@link Result}
	 * @author ray
	 * @return Result&lt;List&lt;T&gt;&gt;
	 */
	public Result<List<T>> toResult(){
		Result<List<T>> result = new Result<List<T>>();
		result.setStatus(ResultCode.SUCCESS);
		result.setCount(this.getTotal());
		result.setTotalPage(this.getPages());
		result.setData(this.getList());
		return result;
	}
}

