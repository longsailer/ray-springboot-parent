package org.ray.crud.base;

import java.io.Serializable;
import java.util.List;

import org.ray.crud.base.vo.QueryParams;

/**
 * 基础增删改查接口
 * @see BaseCrud
 * @author ray
 */
public interface CrudService<T> {
	
	/**
	 * 保存对象
	 * @author ray
	 * @param t
	 * @return
	 * @return int
	 */
	int save(T t);
	/**
	 * 保存多个对象
	 * @author ray
	 * @param t
	 * @return
	 * @return int
	 */
	int save(List<T> tlist);
	/**
	 * 根据ID修改对象
	 * @author ray
	 * @param t
	 * @return
	 * @return int
	 */
	int update(T t);
	/**
	 * 根据多条件修改对象
	 * @author ray
	 * @param t
	 * @return
	 * @return int
	 */
	<P> int update(T t, P p);
	/**
	 * 根据id查询一个对象
	 * @author ray
	 * @param id
	 * @return
	 * @return T
	 */
	<P extends Serializable> T select(P id);
	/**
	 * 根据多个参数查询一个对象
	 * @author ray
	 * @param t
	 * @return
	 * @return T
	 */
	T select(T t);
	/**
	 * 根据[实体中]多个参数查询对象列表
	 * @author ray
	 * @param t
	 * @return
	 * @return List<T>
	 */
	List<T> queryList(T t);
	/**
	 * 根据[自定义对象]多个参数查询对象列表
	 * @author ray
	 * @param p
	 * @return
	 * @return List<T>
	 */
	<P extends QueryParams> List<T> queryList(P p);
}