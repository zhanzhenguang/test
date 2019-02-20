package com.it.zzg.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.it.zzg.modules.sys.entity.SysOrgEntity;


/**
 * 菜单管理
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:42:16
 */
public interface SysOrgService {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysOrgEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysOrgEntity> queryNotButtonList(Map<String, Object> params);
	
	/**
	 * 查询菜单
	 */
	SysOrgEntity queryObject(Long menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<SysOrgEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(SysOrgEntity org);
	
	/**
	 * 修改
	 */
	void update(SysOrgEntity org);
	
	/**
	 * 删除
	 */
	void deleteBatch(Long[] ids);
	
	SysOrgEntity selectOrg(Long userId);

	public void deleteByOrgCode(String orgCode);

	Integer checkOrgCode(String orgCode);
	
}
