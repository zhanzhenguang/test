package com.it.zzg.modules.flow.controller;

import java.util.HashMap;
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
import com.it.zzg.modules.flow.entity.ExecuteHisEntity;
import com.it.zzg.modules.flow.service.ExecuteHisService;
import com.it.zzg.modules.sys.controller.AbstractController;




/**
 * 流程执行历史
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@RestController
@RequestMapping("/flow/executehis")
public class ExecuteHisController extends AbstractController{
    @Autowired
    private ExecuteHisService executeHisService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("flow:executehis:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<ExecuteHisEntity> executeHisList = executeHisService.queryList(query);
        int total = executeHisService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(executeHisList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    
    
  	@RequestMapping("/select/{formId}")
//  	@RequiresPermissions("flow:executehis:list")
  	public R select(@PathVariable("formId") String formId){
  		Map<String, Object> map = new HashMap<>();
  		map.put("formId", formId);
  		List<ExecuteHisEntity> list = executeHisService.queryList(map);
  		return R.ok().put("list", list);
  	}

    /**
     * 信息
     */
    @RequestMapping("/info/{node}")
    @RequiresPermissions("flow:executehis:info")
    public R info(@PathVariable("node") String node){
		ExecuteHisEntity executeHis = executeHisService.queryObject(node);

        return R.ok().put("executeHis", executeHis);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:executehis:save")
    public R save(@RequestBody ExecuteHisEntity executeHis){
			executeHisService.save(executeHis);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("flow:executehis:update")
    public R update(@RequestBody ExecuteHisEntity executeHis){
			executeHisService.update(executeHis);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:executehis:delete")
    public R delete(@RequestBody String[] nodes){
			executeHisService.deleteBatch(nodes);

        return R.ok();
    }
	
}
