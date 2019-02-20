package com.it.zzg.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.common.exception.RRException;
import com.it.zzg.common.validator.Assert;
import com.it.zzg.modules.sys.dao.AppDao;
import com.it.zzg.modules.sys.dao.SysUserAppDao;
import com.it.zzg.modules.sys.entity.AppEntity;
import com.it.zzg.modules.sys.service.AppService;



@Service("appService")
public class AppServiceImpl implements AppService {
	@Autowired
	private AppDao appDao;
	@Autowired
	private SysUserAppDao sysUserAppDao;
	@Override
	public AppEntity queryObject(Long id){
		return appDao.queryObject(id);
	}
	
	@Override
	public List<AppEntity> queryList(Map<String, Object> map){
		return appDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appDao.queryTotal(map);
	}
	
	@Override
	public void save(AppEntity app){
		appDao.save(app);
	}
	
	@Override
	public void update(AppEntity app){
		appDao.update(app);
	}
	
	@Override
	public void delete(Long id){
		appDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		appDao.deleteBatch(ids);
	}

	@Override
	public List<Long> queryAppIdList(Long userId) {
		return appDao.queryAppIdList(userId);
	}

	@Override
	public void saveOrUpdate(Long userId, List<Long> appIdList) {
		if(appIdList==null||appIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		sysUserAppDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("appIdList", appIdList);
		sysUserAppDao.save(map);
		
	}

	@Override
	public HashMap<String, Object> queryByAppId(HashMap<String, Object> param) {
		String id = param.get("id").toString();
        String password = param.get("password").toString();
        AppEntity app = new AppEntity();
        app.setAppid(id);
        app = appDao.queryObject(app);
        Assert.isNull(app, "用户不存在");
        
        //密码错误  
        String userpassword = DigestUtils.sha256Hex(password);
        if(!app.getPassword().equals(userpassword)){
            throw new RRException("密码错误");
        }
        HashMap<String, Object> user = new HashMap<>();
        user.put("user_id", app.getId());
        user.put("password", app.getPassword());
        return user;
	}
	
}
