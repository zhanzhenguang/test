package com.it.zzg.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.modules.sys.dao.SysUserAppDao;
import com.it.zzg.modules.sys.entity.SysUserAppEntity;
import com.it.zzg.modules.sys.service.SysUserAppService;



@Service("sysUserAppService")
public class SysUserAppServiceImpl implements SysUserAppService {
	@Autowired
	private SysUserAppDao sysUserAppDao;
	
	@Override
	public SysUserAppEntity queryObject(Long id){
		return sysUserAppDao.queryObject(id);
	}
	
	@Override
	public List<SysUserAppEntity> queryList(Map<String, Object> map){
		return sysUserAppDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserAppDao.queryTotal(map);
	}
	
	@Override
	public void save(SysUserAppEntity sysUserApp){
		sysUserAppDao.save(sysUserApp);
	}
	
	@Override
	public void update(SysUserAppEntity sysUserApp){
		sysUserAppDao.update(sysUserApp);
	}
	
	@Override
	public void delete(Long id){
		sysUserAppDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysUserAppDao.deleteBatch(ids);
	}
	
}
