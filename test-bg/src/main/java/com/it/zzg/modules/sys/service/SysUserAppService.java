package com.it.zzg.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.it.zzg.modules.sys.entity.SysUserAppEntity;

/**
 * 用户与APP对应关系
 * 
 * @author admin
 * @email admin
 * @date 2018-07-07 18:57:35
 */
public interface SysUserAppService {
	
	SysUserAppEntity queryObject(Long id);
	
	List<SysUserAppEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysUserAppEntity sysUserApp);
	
	void update(SysUserAppEntity sysUserApp);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
