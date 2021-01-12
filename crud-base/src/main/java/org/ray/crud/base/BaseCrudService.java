package org.ray.crud.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.ray.crud.base.impl.CrudServiceImpl;
import org.ray.crud.base.vo.PageVo;

/**
 * BaseCrudService.java
 * <br><br>
 * 对原始增删改查类的封装，所有业务的服务类都要继承它
 * @author: ray
 * @param <M>
 * @date: 2019年12月11日
 */
public abstract class BaseCrudService<T, M extends CrudMapper<T>> extends CrudServiceImpl<T, M> {
	
	protected abstract void setMapper();
	
	@Transactional
	public int update(List<T> tList){
		if(tList == null || tList.size() == 0){
			return 0;
		}
		int count = 0;
		for(T t : tList){
			count += update(t);
		}
		return count;
	}
	
	public PageVo<T> queryPageList(T t, int pageNum, int pageSize){
		List<T> tList = queryList(t);
		PageVo<T> pageInfo = new PageVo<T>(tList);
		return pageInfo;
	}
}

