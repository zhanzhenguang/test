package com.it.zzg.modules.flow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.modules.Constants;
import com.it.zzg.modules.flow.entity.ExecuteRoleEntity;
import com.it.zzg.modules.flow.service.ExecuteRoleService;
import com.it.zzg.modules.sys.controller.AbstractController;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;




/**
 * 流程执行角色表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
@RestController
@RequestMapping("/flow/executerole")
public class ExecuteRoleController extends AbstractController{
    @Autowired
    private ExecuteRoleService executeRoleService;
    @Autowired
	private EhCacheManager ehCacheManager;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("flow:executerole:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<ExecuteRoleEntity> executeRoleList = executeRoleService.queryList(query);
        int total = executeRoleService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(executeRoleList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
  	 * 角色列表
  	 */
  	@RequestMapping("/select/{formId}")
  	@RequiresPermissions("flow:list:list")
  	public R select(@PathVariable("formId") String formId,String nodeCode){
  		Map<String, Object> map = new HashMap<>();
  		if(StringUtils.isNotEmpty(formId)) {
  			map.put("formId", formId);
  			map.put("nodeCode", nodeCode);
  			List<ExecuteRoleEntity> list = executeRoleService.select(map);
  	  		return R.ok().put("list", list);
  		}else {
  			return R.error("获取审批人列表异常");
  		}
  		
  	}
  	
  	 /**
  	 * 角色列表
  	 */
  	@RequestMapping("/selectForUser/{flowCode}")
  	@RequiresPermissions("flow:executerole:list")
  	public R selectForUser(@PathVariable("flowCode") String flowCode){
  		Map<String, Object> map = new HashMap<>();
  		if(StringUtils.isEmpty(flowCode)) {
  			return R.error("流程编码不能为空");
  		}
  		map.put("flowCode", flowCode);
  		List<ExecuteRoleEntity> list = executeRoleService.selectForUser(map);
  		return R.ok().put("list", list);
  	}
    /**
     * 信息
     */
    @RequestMapping("/info/{executeRole}")
    @RequiresPermissions("flow:executerole:info")
    public R info(@PathVariable("executeRole") String executeRole){
		ExecuteRoleEntity executeRoleEntity = executeRoleService.queryObject(executeRole);

        return R.ok().put("executeRole", executeRoleEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:executerole:save")
    public R save(@RequestBody ExecuteRoleEntity executeRole){
		try {
			Map<String,Object> map = new HashMap<>();
	    	map.put("executeRoleCode", executeRole.getExecuteRole());
	    	//map.put("flowCode", executeRole.getFlow());
	    	//map.put("nodeCode", executeRole.getNode());
	    	int count = executeRoleService.queryTotal(map);
	    	
	    	if(count==0){
	    		executeRole.setCreateTime(new Date());
	    		
	    		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
	    		executeRole.setCreateUserId(u.getUserId());
	    		executeRole.setStatus(Constants.STATUS.S_1);
	    		executeRoleService.save(executeRole);
	            return R.ok();
	    	}else{
	    		return R.error(Constants.SYS_MSG.s500);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(Constants.SYS_MSG.s400);
		}
    	
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("flow:executerole:update")
    public R update(@RequestBody ExecuteRoleEntity executeRole){
		try {
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);
    		
    		executeRole.setUpdateTime(new Date());
    		executeRole.setUpdateUserId(u.getUserId());
    		executeRoleService.update(executeRole);
            return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(Constants.SYS_MSG.s400);
		}
		
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:executerole:delete")
    public R delete(@RequestBody String[] executeRoles){
    	try {
    		executeRoleService.deleteBatch(executeRoles);

            return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(Constants.SYS_MSG.s400);
		}
    }
	
}

