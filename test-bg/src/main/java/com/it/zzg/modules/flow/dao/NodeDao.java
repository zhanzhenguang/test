package com.it.zzg.modules.flow.dao;

import com.it.zzg.modules.flow.entity.NodeEntity;
import com.it.zzg.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@Mapper
public interface NodeDao extends BaseDao<NodeEntity> {

	NodeEntity queryObjectByOne(@Param("flowCode")String flowCode, @Param("nodeCode")String nodeCode);
	
}
