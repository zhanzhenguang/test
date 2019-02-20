
package com.it.zzg.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.it.zzg.common.utils.R;
import com.it.zzg.common.utils.ShiroUtils;
import com.it.zzg.modules.flow.service.ExecuteRoleUserService;
import com.it.zzg.modules.sys.entity.SysOrgEntity;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.sys.service.SysOrgService;
import com.it.zzg.modules.sys.service.SysRoleService;
import com.it.zzg.modules.sys.service.SysUserService;
import com.it.zzg.modules.sys.service.SysUserTokenService;
import com.it.zzg.modules.utils.BaseUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 登录相关
 * 
 * @author admin
 * @email admin
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private EhCacheManager ehCacheManager;
	@Autowired
    private ExecuteRoleUserService executeRoleUserService;
	/**
	 * 验证码
	 */
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Map<String, Object> login(String username, String password, String captcha)throws IOException {
		try {
			//本项目已实现，前后端完全分离，但页面还是跟项目放在一起了，所以还是会依赖session
			//如果想把页面单独放到nginx里，实现前后端完全分离，则需要把验证码注释掉(因为不再依赖session了)
			String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
			if(!captcha.equalsIgnoreCase(kaptcha)){
				return R.error("验证码不正确");
			}

			//用户信息
			SysUserEntity user = sysUserService.queryByUserName(username);

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
			}
			
			//账号不存在、密码错误
			if(user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
				return R.error("账号或密码不正确");
			}

			//账号锁定
			if(user.getStatus() == 0){
				return R.error("账号已被锁定,请联系管理员");
			}

			//生成token，并保存到数据库
			R r = sysUserTokenService.createToken(user.getUserId());
			String token = String.valueOf(r.get("token"));
			Integer expire = 360000;
			Cache cache = ehCacheManager.getCacheManager().getCache(com.it.zzg.modules.Constants.TOKEN);
			Element element = new Element(token, user, false, expire,expire);
			cache.put(element);
			SecurityUtils.getSubject().getSession().setAttribute("token", token);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("登录失败");
		}
		
	}


	/**
	 * 退出
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
	public R logout() {
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		sysUserTokenService.logout(u.getUserId());
		return R.ok();
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
	
}
