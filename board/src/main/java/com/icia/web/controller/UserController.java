package com.icia.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icia.common.util.StringUtil;
import com.icia.web.model.Response;
import com.icia.web.model.User;
import com.icia.web.service.UserService;
import com.icia.web.util.CookieUtil;
import com.icia.web.util.HttpUtil;
import com.icia.web.util.JsonUtil;

@Controller("userController")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> login(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userPwd)) {
			
			User user = userService.userSelect(userId);
			
			if(user != null) {
				
				if(StringUtil.equals(userPwd, user.getUserPwd())) {
					
					CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME, CookieUtil.stringToHex(userId));
					
					ajaxResponse.setResponse(0, "Success");
				} else {
					
					ajaxResponse.setResponse(-1, "Password do not match");
				}
							
			} else {
				
				ajaxResponse.setResponse(404, "Not Found");
				
			}
					
		} else {
			
			ajaxResponse.setResponse(400, "Bad Request");
		}
		
		if(logger.isDebugEnabled()) {
			
			logger.debug("[UserController] /user/login response\n" + JsonUtil.toJsonPretty(ajaxResponse));
			
		}
		
		return ajaxResponse;
	
	}
	
	
	@RequestMapping(value="/user/regForm", method=RequestMethod.GET)
	public String userReg(HttpServletRequest request, HttpServletResponse response) {
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
	
		if(StringUtil.isEmpty(cookieUserId)) {
			return "/user/regForm";
		} else {
			CookieUtil.deleteCookie(request, response, AUTH_COOKIE_NAME);
			return "redirect:/";
		}
		
	}
	
	
	@RequestMapping(value="/user/idCheck", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> idCheck(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = HttpUtil.get(request, "userId");
		
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(!StringUtil.isEmpty(userId)) {
			
			if(userService.userSelect(userId) == null) {
				ajaxResponse.setResponse(0, "Success");
			} else {
				ajaxResponse.setResponse(100, "Duplicate ID");
			}

		} else {
			ajaxResponse.setResponse(400, "Bad Reqeust");
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("[UserController] /user/idCheck response\n" + JsonUtil.toJsonPretty(ajaxResponse));
		}
		
		return ajaxResponse;
	}
	
	
	@RequestMapping(value="/user/regProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> regProc(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		String userName = HttpUtil.get(request, "userName");
		String userEmail = HttpUtil.get(request, "userEmail");
		
		Response<Object> ajaxResponse = new Response<Object>();
		
		if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userPwd) && !StringUtil.isEmpty(userName) && !StringUtil.isEmpty(userEmail)) {
				
			if(userService.userSelect(userId) == null) {
				
				User user = new User();
				user.setUserId(userId);
				user.setUserPwd(userPwd);
				user.setUserName(userName);
				user.setUserEmail(userEmail);
				user.setStatus("Y");
			
				if(userService.userInsert(user) > 0) {
					ajaxResponse.setResponse(0, "Success");
				} else {
					ajaxResponse.setResponse(500, "Internal Server Error");
				}
			} else {
				ajaxResponse.setResponse(100, "Duplicate ID");
			}
		} else {
			ajaxResponse.setResponse(400, "Bad Request");
		}
		return ajaxResponse;
	}
	
	
	
	
	
	
	
}
