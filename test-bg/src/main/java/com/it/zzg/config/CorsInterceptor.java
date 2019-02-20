package com.it.zzg.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CorsInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception{

	  /*  String origin = httpServletRequest.getHeader("Origin");
	    httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
	    httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
	    httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
	    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");*/
	
	    //跨域访问CORS
	    httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
	    httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
	    httpServletResponse.addHeader("Access-Control-Allow-Headers", "S_ID,content-type");
	    httpServletResponse.addHeader("Access-Control-Max-Age", "3600000");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        
        //让请求，不被缓存，
        httpServletResponse.setHeader("Cache-Control", "no-cache");  
        httpServletResponse.setHeader("Cache-Control", "no-store");  
        httpServletResponse.setHeader("Pragma", "no-cache");  
        httpServletResponse.setDateHeader("Expires", 0); 
	    return true;
    }

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
