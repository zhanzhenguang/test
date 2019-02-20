package com.it.zzg.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.it.zzg.modules.sys.entity.SysOrgEntity;

@Mapper
public interface SysOrgDao extends BaseDao<SysOrgEntity> {
	
	List<SysOrgEntity> queryListParentId(Long parentId);
	List<SysOrgEntity> queryNotButtonList(Map<String, Object> params);
	
	SysOrgEntity selectOrg(Map<String, Object> map);

	public void deleteByOrgCode(Map<String,Object> map);

	Integer checkOrgCode(@Param("orgCode")String orgCode);
}
