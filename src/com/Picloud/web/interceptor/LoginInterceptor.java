package com.Picloud.web.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginInterceptor
 */
@WebFilter("/LoginInterceptor")
public class LoginInterceptor implements Filter {

	 String[] IGNORE_PAGE = new String[] { "login.jsp", "register.jsp","/login","/register","/resources","/server","/process"};  

    public LoginInterceptor() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;    
        HttpServletRequest req=(HttpServletRequest) request; 
        String uri = req.getRequestURI();
        
        boolean doFilter = true;  
        for (String s : IGNORE_PAGE) {  
            if (uri.indexOf(s) != -1) {  
                // 如果uri中包含不过滤的uri，则不进行过滤  
                doFilter = false;  
                break;  
            }  
        }  
        if(doFilter){
        	HttpSession session =  req.getSession();
        	Object obj = session.getAttribute("LoginUser");  
        	if(obj == null)
        	{
        		String message = "请先登录";
        		session.setAttribute("LOGIN_MSG", message);
        		res.sendRedirect("/Picloud/login.jsp");
        	}
        	
        }
		res.setHeader("Access-Control-Allow-Origin", "*");
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
