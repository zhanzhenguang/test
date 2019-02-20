package com.it.zzg.modules.sys.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.google.gson.Gson;
import com.it.zzg.common.utils.R;
import com.it.zzg.common.utils.SpringContextUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * oauth2过滤器
 *
 * @author admin
 * @email admin
 * @date 2017-05-20 13:00
 */
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
    	//System.out.println(SecurityUtils.getSubject().getPrincipals());
    	
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
        	HttpServletRequest httpRequest = (HttpServletRequest) request;
        	String url = httpRequest.getRequestURL().toString();
        	if(url.lastIndexOf(".png")>0) {
        		return true;
        	}
        	if(url.lastIndexOf(".jpg")>0) {
        		return true;
        	}
        	if(url.lastIndexOf(".jpeg")>0) {
        		return true;
        	}
        	if(url.lastIndexOf(".xls")>0) { 
        		return true;
        	}
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);

            return false;
        }
        if(SecurityUtils.getSubject().getSession().getAttribute("token")==null) {
        	if(org.apache.commons.lang3.StringUtils.isNotEmpty(token)) {
        		SecurityUtils.getSubject().getSession().setAttribute("token",token);
        	}else {
        		HttpServletResponse httpResponse = (HttpServletResponse) response;
                String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
                httpResponse.getWriter().print(json);
                return false;
        	}
        }
       /* 
        String stoken = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
    	if(org.apache.commons.lang3.StringUtils.isEmpty(stoken)){
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);
            return false;
    	}
    	if(!token.equals(stoken)){
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);
            return false;
    	}*/
        EhCacheManager ehCacheManager = SpringContextUtils.getBean("ehCacheManager",EhCacheManager.class);
        Cache cache = ehCacheManager.getCacheManager().getCache(com.it.zzg.modules.Constants.TOKEN);
        Element el = cache.get(token);
        if(el==null){
        	 HttpServletResponse httpResponse = (HttpServletResponse) response;
             String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
             httpResponse.getWriter().print(json);

             return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }


}
