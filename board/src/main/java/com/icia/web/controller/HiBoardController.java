package com.icia.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icia.common.util.StringUtil;
import com.icia.web.util.CookieUtil;
import com.icia.web.util.HttpUtil;

@Controller("hiBoardController")
public class HiBoardController {

	private static Logger logger = LoggerFactory.getLogger(HiBoardController.class);
	
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public String boardList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchVale", "");
		

		if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
			
			
			
			
		} else {
			
			
		}	
			
	
		return "/board/list";
	}
}
