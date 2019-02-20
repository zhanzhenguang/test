package com.it.zzg.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;

/**
 * 继承通用mapper
 */
public interface MyMapper<T> extends Mapper<T>, InsertSelectiveMapper<T>, MySqlMapper<T> {
}
