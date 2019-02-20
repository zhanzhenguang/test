package com.it.zzg.modules.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.CaseFormat;
import com.it.zzg.common.annotation.SysLog;
import com.it.zzg.common.utils.Constant;
import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.common.validator.Assert;
import com.it.zzg.common.validator.ValidatorUtils;
import com.it.zzg.common.validator.group.AddGroup;
import com.it.zzg.common.validator.group.UpdateGroup;
import com.it.zzg.modules.flow.service.ExecuteRoleUserService;
import com.it.zzg.modules.sys.entity.SysOrgEntity;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.sys.service.SysOrgService;
import com.it.zzg.modules.sys.service.SysRoleService;
import com.it.zzg.modules.sys.service.SysUserOrgRelService;
import com.it.zzg.modules.sys.service.SysUserRoleService;
import com.it.zzg.modules.sys.service.SysUserService;
import com.it.zzg.modules.utils.BaseUtils;

/**
 * 系统用户
 * 
 * @author admin
 * @email admin
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserOrgRelService sysUserOrgRelService;
	@Autowired
    private ExecuteRoleUserService executeRoleUserService;
	@Autowired
   	private EhCacheManager ehCacheManager;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysRoleService sysRoleService;
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		if(u.getUserId() != Constant.SUPER_ADMIN){
			//params.put("createUserId", getUserId());
	    	//SysOrgEntity sysOrgEntity = u.getSysOrgEntity();
			//params.put("orgId", sysOrgEntity.getId());
			if(u.getOrgIdList()!=null) {
				params.put("orgList", u.getOrgIdList());
			}
		}
		if (params.containsKey("sidx")) {
        	String sidx = params.get("sidx").toString();
        	sidx = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sidx);
        	params.put("sidx",sidx);
        }
		
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		try {
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);
			return R.ok().put("user", u);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("服务异常，请联系管理员。");
		}
	}
	/**
	 * 刷新缓存
	 */
	@RequestMapping("/refreshCache/{userId}")
	public R refreshCache(@PathVariable("userId") Long userId){
		try {
			//用户信息
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);
			SysUserEntity user = sysUserService.queryByUserName(u.getUsername());

			//角色
			if(user!=null){
				List<Long> roles = sysRoleService.queryRoleIdList(user.getUserId());
				user.setRoleIdList(roles);
				//机构
				SysOrgEntity sysOrgEntity = sysOrgService.selectOrg(user.getUserId());
				user.setSysOrgEntity(sysOrgEntity);
				
				List<Long> orgIdList = new ArrayList<Long>();
				if(null!=sysOrgEntity)
				{
					orgIdList.add(sysOrgEntity.getId());
					//获取全部机构（包含所有子机构）
					getAllOrgs(sysOrgEntity.getId(), orgIdList);
				}
				user.setOrgIdList(orgIdList);
				
				List<String> flowRoleIdList = executeRoleUserService.queryRoleIdList(user.getUserId());
				List<String> flowRoleNameList = executeRoleUserService.queryRoleNameList(user.getUserId());
				
				user.setFlowRoleNameList(flowRoleNameList);
				user.setFlowRoleIdList(flowRoleIdList);
				BaseUtils.setUser(ehCacheManager, user);
				return R.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}
	private void getAllOrgs(Long parentOrgId, List<Long> orgList) {
		List<SysOrgEntity> subList = sysOrgService.queryListParentId(parentOrgId);
		if(subList != null && subList.size() > 0) {
			for(SysOrgEntity orgBean : subList) {
				if(!orgList.contains(orgBean.getId())) {
					orgList.add(orgBean.getId());
				}
				getAllOrgs(orgBean.getId(), orgList);
			}
		}
	}
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		//sha256加密
		password = new Sha256Hash(password,u.getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword,u.getSalt()).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(u.getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		//获取用户所属的角色列表
		List<Long> orgIdList = sysUserOrgRelService.queryOrgIdList(userId);
		user.setOrgIdList(orgIdList);
		
		/*List<Long> appIdList = appService.queryAppIdList(userId);
		user.setAppIdList(appIdList);*/
		
		List<String> flowRoleIdList = executeRoleUserService.queryRoleIdList(userId);
		List<String> flowRoleNameList = executeRoleUserService.queryRoleNameList(userId);
		
		user.setFlowRoleNameList(flowRoleNameList);
		user.setFlowRoleIdList(flowRoleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		try {
			ValidatorUtils.validateEntity(user, AddGroup.class);
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);
			SysUserEntity userEntity = sysUserService.queryByUserName(user.getUsername());
			if(userEntity==null) {
				user.setCreateUserId(u.getUserId());
				sysUserService.save(user);
				return R.ok();
			}else {
				return R.error("已经存在相同用户名称");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存错误");
		}
		
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		try {
			ValidatorUtils.validateEntity(user, UpdateGroup.class);
			
//			SysUserEntity userEntity = sysUserService.queryByUserName(user.getUsername());
//			if(userEntity==null) {
				sysUserService.update(user);
				return R.ok();
//			}else {
//				return R.error("已经存在相同用户名称");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存错误");
		}
	}
	/**
	 * 修改用户
	 */
	@SysLog("重置用户")
	@RequestMapping("/reset/{userId}")
	@RequiresPermissions("sys:user:update")
	public R reset(@PathVariable("userId") Long userId){
		if(userId==null){
			return R.error("用户id不能为空");
		}
		SysUserEntity user = sysUserService.queryObject(userId);
		//sha256加密
		String password = new Sha256Hash("111111", user.getSalt()).toHex();
		//更新密码
		int count = sysUserService.resetPassword(userId, password);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		if(ArrayUtils.contains(userIds, u.getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
	@SysLog("角色分配用户")
	@RequestMapping("/assignUser")
	@RequiresPermissions("sys:role:assignUser")
	public R assignUser(@RequestParam("userIds[]") Long[] userIds, String roleId){
		try {
			Long uid = null;
			int ss = 0;
			for(Long userId : userIds) {
				ss = sysUserRoleService.querycount(userId);
				if(ss!=0) {
					uid = userId;
					break;
				}
			}
			if(uid!=null) {
				SysUserEntity u = sysUserService.queryObject(uid);
				return R.error("用户【"+u.getUsername()+"-"+u.getUserId()+"】不能拥有多个角色");
			}else {
				sysUserRoleService.insert(userIds, roleId);
				return R.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		
	}
	
	@RequestMapping("/userRoleList")
	@RequiresPermissions("sys:role:assignUser")
	public R userRoleList(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		if(u.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", u.getUserId());
		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryListByRole(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	@RequestMapping("/cancelUser")
	@RequiresPermissions("sys:role:assignUser")
	public R cancelUser(Long roleId, Long userId){
		sysUserRoleService.cancelUser(roleId, userId);
		
		return R.ok();
	}
}
