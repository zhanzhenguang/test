package com.it.zzg.modules.flow.dao;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.flow.entity.ExecuteHisEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

/**
 * 流程执行历史
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@Mapper
public interface ExecuteHisDao extends BaseDao<ExecuteHisEntity> {

	void updateByFormId(String formId);
	
}
