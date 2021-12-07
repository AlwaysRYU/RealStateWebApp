package com.mycom.happyhouse.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycom.happyhouse.dto.UserDto;



@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//async
		System.out.println(">>>>>>>preHandle 인터셉터 호출됨 >>>>>>>>>>>>" + request.getRequestURI() );
		
		String async = request.getHeader("async");
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        System.out.println("지금 로그인 중인 사람 : " + userDto.getUserName());
        
        
        if ( request.getMethod().equals("OPTIONS")  ) {
        	
        	return true;
        }
        
        if( userDto == null ) {
        	
//        	if("true".equals(async)) {
//				Gson gson = new Gson();
//
//				JsonObject jsonObject = new JsonObject();
//				jsonObject.addProperty("result", "login");
//				
//				String jsonStr = gson.toJson(jsonObject);
//				response.getWriter().write(jsonStr);
//			}else {
//				response.sendRedirect("/");
//			}
//
//        	return false;
        	Gson gson = new Gson();

			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", "login");
			
			String jsonStr = gson.toJson(jsonObject);
			response.getWriter().write(jsonStr);

        	return false;
        }

        return true;
    }
}
