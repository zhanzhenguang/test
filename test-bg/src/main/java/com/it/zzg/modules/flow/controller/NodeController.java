package com.it.zzg.modules.flow.controller;

import java.util.Date;
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
import com.it.zzg.modules.flow.entity.NodeEntity;
import com.it.zzg.modules.flow.service.NodeService;
import com.it.zzg.modules.sys.controller.AbstractController;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;




/**
 * 流程节点表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@RestController
@RequestMapping("/flow/node")
public class NodeController extends AbstractController{
    @Autowired
    private NodeService nodeService;
    @Autowired
	private EhCacheManager ehCacheManager;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("flow:node:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<NodeEntity> nodeList = nodeService.queryList(query);
        int total = nodeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(nodeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{flowCode}/{nodeCode}")
    @RequiresPermissions("flow:node:info")
    public R info(@PathVariable("nodeCode") String nodeCode,@PathVariable("flowCode") String flowCode){
		NodeEntity node = nodeService.queryObjectByOne(flowCode,nodeCode);

        return R.ok().put("node", node);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:node:save")
    public R save(@RequestBody NodeEntity node){
			
		try {
	    	String nodeCode = node.getNodeCode();
	    	NodeEntity nodeCount = nodeService.queryObjectByOne(node.getFlowCode(),nodeCode);
	    	
	    	if(nodeCount==null){
	    		node.setCreateTime(new Date());
	    		
	    		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
	    		node.setCreateUserId(u.getUserId());
	    		node.setStatus(Constants.STATUS.S_1);
	    		nodeService.save(node);
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
    @RequiresPermissions("flow:node:update")
    public R update(@RequestBody NodeEntity node){
        try {
        	node.setUpdateTime(new Date());
        	//NodeEntity nodeCount = nodeService.queryObjectByOne(node.getFlowCode(),node.getNodeCode());
        	//if(nodeCount==null) {
        		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
        		node.setUpdateUserId(u.getUserId());
        		node.setStatus(Constants.STATUS.S_1);
        		nodeService.update(node);
                return R.ok();
        	////}else {
        	//	return R.error("主键重复");
        	//}
    		
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(Constants.SYS_MSG.s400);
		}
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:node:delete")
    public R delete(@RequestBody String[] nodeCodes){
			nodeService.deleteBatch(nodeCodes);

        return R.ok();
    }
	
}
