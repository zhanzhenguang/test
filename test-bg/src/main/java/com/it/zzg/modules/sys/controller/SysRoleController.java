package com.it.zzg.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.annotation.SysLog;
import com.it.zzg.common.utils.Constant;
import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.common.validator.ValidatorUtils;
import com.it.zzg.modules.sys.entity.SysRoleEntity;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.sys.service.SysRoleMenuService;
import com.it.zzg.modules.sys.service.SysRoleService;
import com.it.zzg.modules.utils.BaseUtils;

/**
 * 角色管理
 * 
 * @author admin
 * @email admin
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
   	private EhCacheManager ehCacheManager;
//	@RequestMapping("/toAssignUser")
//	public ModelAndView toAssignUser(){
//		try{
//			ModelAndView view = new ModelAndView("modules/sys/role");
//			return view;
//		}catch(Throwable e){
//			return null;
//		}
//	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		/*SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		if(u.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", u.getUserId());
		}
		*/
		//查询列表数据
		Query query = new Query(params);
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	//@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
//		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		/*if(u.getUserId() != Constant.SUPER_ADMIN){
			map.put("createUserId", u.getUserId());
		}*/
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		role.setCreateUserId(u.getUserId());
		sysRoleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		role.setCreateUserId(u.getUserId());
		sysRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
