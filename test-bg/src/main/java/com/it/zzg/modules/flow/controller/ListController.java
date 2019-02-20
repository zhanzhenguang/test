package com.it.zzg.modules.flow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.it.zzg.modules.flow.entity.ListEntity;
import com.it.zzg.modules.flow.service.ListService;
import com.it.zzg.modules.sys.controller.AbstractController;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;




/**
 * 流程表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@RestController
@RequestMapping("/flow/list")
public class ListController extends AbstractController{
    @Autowired
    private ListService listService;
    @Autowired
	private EhCacheManager ehCacheManager;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("flow:list:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<ListEntity> listList = listService.queryList(query);
        int total = listService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(listList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    @RequestMapping("/select")
    @RequiresPermissions("flow:list:list")
    public R select(){
        List<ListEntity> list = listService.queryList(null);

        return R.ok().put("list", list);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{flowCode}")
    @RequiresPermissions("flow:list:info")
    public R info(@PathVariable("flowCode") String flowCode){
			
    	ListEntity list = listService.queryObject(flowCode);

        return R.ok().put("list", list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:list:save")
    public R save(@RequestBody ListEntity list){

        try {
			Map<String,Object> map = new HashMap<>();
	    	map.put("flowCode", list.getFlowCode());
	    	int count = listService.queryTotal(map);
	    	
	    	if(count==0){
	    		list.setCreateTime(new Date());
	    		
	    		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
	    		list.setCreateUserId(u.getUserId());
	    		list.setStatus(Constants.STATUS.S_1);
	    		listService.save(list);
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
    @RequiresPermissions("flow:list:update")
    public R update(@RequestBody ListEntity list){
			

        try {
        	list.setUpdateTime(new Date());
    		
    		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
    		list.setUpdateUserId(u.getUserId());
    		list.setStatus(Constants.STATUS.S_1);
    		listService.update(list);
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
    @RequiresPermissions("flow:list:delete")
    public R delete(@RequestBody String[] flowCodes){
			listService.deleteBatch(flowCodes);

        return R.ok();
    }
	
}
