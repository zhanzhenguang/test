package com.it.zzg.modules.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.annotation.SysLog;
import com.it.zzg.common.exception.RRException;
import com.it.zzg.common.utils.Constant;
import com.it.zzg.common.utils.R;
import com.it.zzg.modules.Constants;
import com.it.zzg.modules.sys.entity.SysOrgEntity;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.sys.service.SysOrgService;
import com.it.zzg.modules.utils.BaseUtils;

@RestController
@RequestMapping("/sys/org")
public class SysOrgController extends AbstractController {
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
   	private EhCacheManager ehCacheManager;
	/**
	 * 所有机构列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:org:list")
	public List<SysOrgEntity> list(){
		List<SysOrgEntity> orgList = sysOrgService.queryList(new HashMap<String, Object>());

		return orgList;
	}
	
	@RequestMapping("/select")
	//@RequiresPermissions("sys:org:select")
	public R select(){
		 String orgId = "0";
		//查询列表数据
		List<SysOrgEntity> orgList = null;
		Long id = 0L;
		Map<String, Object> params = new HashMap<>();
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		if(u.getUserId() == Constant.SUPER_ADMIN){
			orgList = sysOrgService.queryNotButtonList(params);
		}else {
			params.put("orgList", u.getOrgIdList());
			orgList = sysOrgService.queryNotButtonList(params);
			SysOrgEntity sysOrgEntity = u.getSysOrgEntity();
			orgId = String.valueOf(sysOrgEntity.getId());
			int i = 0; 
			for(SysOrgEntity org : orgList) {
				if(org.getId().equals(sysOrgEntity.getId())) {
					org.setParentId(id);
					orgList.set(i, org);
					break;
				}
				i += 1;
			}
		}
		String orgName = BaseUtils.getStringFromCache(ehCacheManager, Constants.orgName);
		if(orgName==null||orgName.equals("null")||orgName.equals("")){
			orgName = "卜蜂莲花";
		}
		//添加顶级机构
		SysOrgEntity root = new SysOrgEntity();
		root.setId(id);
		root.setOrgName(orgName);
		root.setParentId(-1L);
		root.setOpen(true);
		orgList.add(root);
		
		return R.ok().put("orgList", orgList).put("CurOrgId",orgId);
	}
	
	/**
	 * 机构信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:org:info")
	public R info(@PathVariable("id") Long orgId){
		SysOrgEntity org = sysOrgService.queryObject(orgId);
		return R.ok().put("org", org);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存机构")
	@RequestMapping("/save")
	@RequiresPermissions("sys:org:save")
	public R save(@RequestBody SysOrgEntity org){

		//IdWorker idWorker = new IdWorker(0, 0);
		String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		//Long ids = Long.valueOf(idWorker.nextStringId());
		//org.setId(ids);
		org.setCreateTime(new Date());
		org.setCreateUser(username);
		org.setUpdateTime(new Date());
		org.setUpdateUser(username);
		//数据校验
		verifyForm(org);
		Integer counts = sysOrgService.checkOrgCode(org.getOrgCode());
		if(counts>0) {
			throw new RRException("机构编码已经存在");
		}
		sysOrgService.save(org);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改机构")
	@RequestMapping("/update")
	@RequiresPermissions("sys:org:update")
	public R update(@RequestBody SysOrgEntity org){
		//数据校验
		verifyForm(org);
		sysOrgService.update(org);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除机构")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:org:delete")
	public R delete(long id){
		List<SysOrgEntity> orgList = sysOrgService.queryListParentId(id);
		if(orgList.size() > 0){
			return R.error("请先删除子机构");
		}

		sysOrgService.deleteBatch(new Long[]{id});
		
		return R.ok();
	}

	/**
	 * 检查orgCode是否存在
	 */
	@SysLog("检查orgCode")
	@RequestMapping("/checkOrgCode")
//	@RequiresPermissions("sys:org:checkOrgCode")
	public R checkOrgCode(String orgCode){
		System.out.println(orgCode);
		//String counts = sysOrgService.checkOrgCode(orgCode);
		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysOrgEntity org){
		if(StringUtils.isBlank(org.getOrgName())){
			throw new RRException("机构名称不能为空");
		}
		if(StringUtils.isBlank(org.getOrgCode())){
			throw new RRException("机构编码不能为空");
		}
		if(org.getParentId() == null){
			throw new RRException("上级机构不能为空");
		}
		
	}
}
