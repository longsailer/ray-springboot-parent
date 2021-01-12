package org.ray.crud.base;


import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import org.ray.crud.base.provider.SpecialInsertProvider;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * CrudMapper.java
 * <br><br>
 * 基础Mapper
 * @author: ray
 * @date: 2019年12月11日
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface CrudMapper<T> extends Mapper<T>, MySqlMapper<T> {
	
    @InsertProvider(type=SpecialInsertProvider.class, method="dynamicSQL")
    int saveList(List<T> recordList);
}