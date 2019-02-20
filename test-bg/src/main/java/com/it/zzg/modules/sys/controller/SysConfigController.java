package com.it.zzg.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.annotation.SysLog;
import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.common.validator.ValidatorUtils;
import com.it.zzg.modules.Constants;
import com.it.zzg.modules.sys.entity.SysConfigEntity;
import com.it.zzg.modules.sys.service.SysConfigService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 系统参数信息
 * 
 * @author admin
 * @email admin
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
   	private EhCacheManager ehCacheManager;
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysConfigEntity> configList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.queryObject(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return R.ok();
	}
	/**
	 * 刷新缓存
	 */
	@SysLog("刷新缓存")
	@RequestMapping("/refreshCache/{type}")
	@RequiresPermissions("sys:config:update")
	public @ResponseBody R refreshCache(@PathVariable("type") String type){
		try {
			if(type.equals("fee")) {
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.RENT);
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.RENTER_DEPOSIT);
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.WATER_DEPOSIT);
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.BUILD_DEPOSIT);
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.SERVICE_FEE_YEAR);
				setFeeCache( Constants.CACHE_REC, Constants.CACHE_REC_I.SERVICE_FEE_MONTH);
			}else {
				List<SysConfigEntity> configList = sysConfigService.queryList(null);
				SysConfigEntity config = null;
				for (int i = 0; i < configList.size(); i++) {
					config = configList.get(i);
					setConfigCache(Constants.CACHE_REC, config.getKey(),config.getValue());
				}
			}
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		
	}
	private void setConfigCache(String cacheKey,String key,String value){
		try {
			Cache cache = ehCacheManager.getCacheManager().getCache(cacheKey);
			if(cache==null){
				ehCacheManager.getCacheManager().addCache(cacheKey);
				cache = ehCacheManager.getCacheManager().getCache(cacheKey);
			}
			
			if(cache.isElementInMemory(key)){
				cache.remove(key);
			}
			Element element = new Element(key, value);
			cache.put(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void setFeeCache(String cacheKey,String valueKey){
		try {
			Cache cache = ehCacheManager.getCacheManager().getCache(cacheKey);
			Element element = null;
			if(cache==null){
				ehCacheManager.getCacheManager().addCache(cacheKey);
				cache = ehCacheManager.getCacheManager().getCache(cacheKey);
			}
			
			if(cache.isElementInMemory(valueKey)){
				cache.remove(valueKey);
			}
			cache.put(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return R.ok();
	}

}
