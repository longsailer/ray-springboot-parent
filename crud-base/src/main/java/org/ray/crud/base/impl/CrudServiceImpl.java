package org.ray.crud.base.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.ray.crud.base.CrudMapper;
import org.ray.crud.base.CrudService;
import org.ray.crud.base.vo.QueryParams;

import tk.mybatis.mapper.entity.Example;

/**
 * BaseCrudService.java
 * <br><br>
 * 实现增删改查的原始服务类
 * @author: ray
 * @date: 2019年12月11日
 */
public class CrudServiceImpl<T, M extends CrudMapper<T>> implements CrudService<T> {

	protected M m;
	
	public M getMapper(){
		return m;
	}
	
	@Transactional
	public int save(T t) {
		return m.insertSelective(t);
	}
	
	@Transactional
	public int save(List<T> tlist) {
		return m.saveList(tlist);
	}

	@Transactional
	public int update(T t) {
		return m.updateByPrimaryKeySelective(t);
	}
	
	@Transactional
	public <P> int update(T t, P p) {
		return m.updateByExampleSelective(t, p);
	}

	public T select(T t) {
		return m.selectOne(t);
	}

	public <P extends Serializable> T select(P id) {
		return m.selectByPrimaryKey(id);
	}

	public List<T> queryList(T t) {
		return m.select(t);
	}
	
	public <P extends QueryParams> List<T> queryList(P p) {
		return m.selectByExample(p);
	}
	
	public <E extends Example> List<T> selectByExample(E example) {
		return m.selectByExample(example);
	}
}

