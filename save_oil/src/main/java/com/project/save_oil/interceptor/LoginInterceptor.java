package com.project.save_oil.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		
		String id = (String)request.getSession().getAttribute("uId_Session");
		if(!requestURI.equals("/login") || !requestURI.equals("/register")) {
			if(id == null) {
				response.setCharacterEncoding("UTF-8");
				PrintWriter wr = response.getWriter();
				wr.print("<script>alert('로그인 이후 사용 가능합니다.');</script>");
				return false;
			}
		}
		
		return true;
	}

	
	
}
