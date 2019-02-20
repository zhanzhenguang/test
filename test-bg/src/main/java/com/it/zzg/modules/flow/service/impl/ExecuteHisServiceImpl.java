package com.it.zzg.modules.flow.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.modules.flow.dao.ExecuteHisDao;
import com.it.zzg.modules.flow.entity.ExecuteHisEntity;
import com.it.zzg.modules.flow.service.ExecuteHisService;



@Service("executeHisService")
public class ExecuteHisServiceImpl implements ExecuteHisService {
	@Autowired
	private ExecuteHisDao executeHisDao;
	
	@Override
	public ExecuteHisEntity queryObject(String node){
		return executeHisDao.queryObject(node);
	}
	
	@Override
	public List<ExecuteHisEntity> queryList(Map<String, Object> map){
		return executeHisDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return executeHisDao.queryTotal(map);
	}
	
	@Override
	public void save(ExecuteHisEntity executeHis){
		executeHisDao.save(executeHis);
	}
	
	@Override
	public void update(ExecuteHisEntity executeHis){
		executeHisDao.update(executeHis);
	}
	
	@Override
	public void delete(String node){
		executeHisDao.delete(node);
	}
	
	@Override
	public void deleteBatch(String[] nodes){
		executeHisDao.deleteBatch(nodes);
	}

	@Override
	public void updateByFormId(String formId) {
		executeHisDao.updateByFormId(formId);
	}
	
}
