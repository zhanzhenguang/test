package com.it.zzg.modules.sys.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.it.zzg.common.utils.MyCustomDateEditor;

/**
 * Controller公共组件
 * 
 * @author admin
 * @email admin
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/*protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}*/
	/**
	 * 
    * @Title: initBinder
    * @Description: Controller层接收表单提交的数据时,Sring转换为Date .
    * @param @param binder
    * @param @throws Exception    参数
    * @return void    返回类型
    * @throws
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		// 注册自定义的属性编辑器
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		// 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
		//binder.registerCustomEditor(Date.class, dateEditor);
		binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
	}
	
}
