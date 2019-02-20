package com.it.zzg.modules.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.zzg.modules.Constants;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.sys.service.SysConfigService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class BaseUtils {

	private static Logger logger = LoggerFactory.getLogger(BaseUtils.class);
    /**
     * 获取请求的token
     */
	public static String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }
	
    /**
     * 获取请求的token
     */
	public static SysUserEntity getUser(EhCacheManager ehCacheManager){
		Cache cache = ehCacheManager.getCacheManager().getCache(com.it.zzg.modules.Constants.TOKEN);
		//String token = SecurityUtils.getSubject().getSession().setAttribute("token");
		String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
		Element element = cache.get(token);
		SysUserEntity u = (SysUserEntity) element.getObjectValue();
        return u;
    }
	public static int setUser(EhCacheManager ehCacheManager,SysUserEntity user){
		try {
			Cache cache = ehCacheManager.getCacheManager().getCache(com.it.zzg.modules.Constants.TOKEN);
			//String token = BaseUtils.getRequestToken(request);
			String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
			Integer expire = 360000;
			cache.remove(token);
			Element element = new Element(token, user, false, expire,expire);
			cache.put(element);
	        return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
    }
	public static String getStringFromCache(EhCacheManager ehCacheManager,String valueKey){
		
		Cache cache = ehCacheManager.getCacheManager().getCache(Constants.CACHE_REC);
		Element element = null;
		if(cache==null){
			ehCacheManager.getCacheManager().addCache(Constants.CACHE_REC);
			cache = ehCacheManager.getCacheManager().getCache(Constants.CACHE_REC);
		}
		
		if(cache.isElementInMemory(valueKey)){
			element = cache.get(valueKey);
			return (String) element.getValue();
		}else{
			return null;
		}
	}

	public static String getStringFromCache(EhCacheManager ehCacheManager,String cacheKey,String valueKey){
		
		Cache cache = ehCacheManager.getCacheManager().getCache(cacheKey);
		Element element = null;
		if(cache==null){
			ehCacheManager.getCacheManager().addCache(cacheKey);
			cache = ehCacheManager.getCacheManager().getCache(cacheKey);
		}
		
		if(cache.isElementInMemory(valueKey)){
			element = cache.get(valueKey);
			return (String) element.getValue();
		}else{
			return null;
		}
	}
	
	public static String getStringFromCache(SysConfigService sysConfigService,EhCacheManager ehCacheManager,String cacheKey,String valueKey){
		Cache cache = ehCacheManager.getCacheManager().getCache(cacheKey);
		Element element = null;
		if(cache==null){
			ehCacheManager.getCacheManager().addCache(cacheKey);
			cache = ehCacheManager.getCacheManager().getCache(cacheKey);
		}
		
		if(cache.isElementInMemory(valueKey)){
			element = cache.get(valueKey);
		}else{
			String value = sysConfigService.getValue(valueKey);
			if(value==null){
				logger.error(" sysConfigService.getValue("+valueKey+")==null");
			}
			element = new Element(valueKey, value);
			cache.put(element);
		}
		element = cache.get(valueKey);
		return (String) element.getValue();
	}
}
