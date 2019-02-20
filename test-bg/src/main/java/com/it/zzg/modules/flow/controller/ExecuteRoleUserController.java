package com.it.zzg.modules.flow.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.modules.flow.entity.ExecuteRoleUserEntity;
import com.it.zzg.modules.flow.service.ExecuteRoleUserService;
import com.it.zzg.modules.sys.controller.AbstractController;




/**
 * 流程执行角色用户中间表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
@RestController
@RequestMapping("/flow/executeroleuser")
public class ExecuteRoleUserController extends AbstractController{
    @Autowired
    private ExecuteRoleUserService executeRoleUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("flow:executeroleuser:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<ExecuteRoleUserEntity> executeRoleUserList = executeRoleUserService.queryList(query);
        int total = executeRoleUserService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(executeRoleUserList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{executeRole}")
    @RequiresPermissions("flow:executeroleuser:info")
    public R info(@PathVariable("executeRole") String executeRole){
			ExecuteRoleUserEntity executeRoleUser = executeRoleUserService.queryObject(executeRole);

        return R.ok().put("executeRoleUser", executeRoleUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:executeroleuser:save")
    public R save(@RequestBody ExecuteRoleUserEntity executeRoleUser){
			executeRoleUserService.save(executeRoleUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("flow:executeroleuser:update")
    public R update(@RequestBody ExecuteRoleUserEntity executeRoleUser){
			executeRoleUserService.update(executeRoleUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:executeroleuser:delete")
    public R delete(@RequestBody String[] executeRoles){
			executeRoleUserService.deleteBatch(executeRoles);

        return R.ok();
    }
	
}
