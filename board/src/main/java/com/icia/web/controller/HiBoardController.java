package com.icia.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icia.common.util.StringUtil;
import com.icia.web.model.HiBoard;
import com.icia.web.model.Paging;
import com.icia.web.model.User;
import com.icia.web.service.HiBoardService;
import com.icia.web.service.UserService;
import com.icia.web.util.CookieUtil;
import com.icia.web.util.HttpUtil;

@Controller("hiBoardController")
public class HiBoardController {

	private static Logger logger = LoggerFactory.getLogger(HiBoardController.class);
	
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private HiBoardService hiBoardService;
	
	private static final int LIST_COUNT = 5;	//한 페이지 게시물 수
	private static final int PAGE_COUNT = 5;	//페징 수
	
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public String boardList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchVale", "");
		long curPage = HttpUtil.get(request, "curPage", (long)0);
		
		Paging paging = null;
		HiBoard search = null;
		
		int totalCount = 0;
		List<HiBoard> list = null;
		
		if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {	
			search.setSearchType(searchType);
			search.setSearchValue(searchValue);
		}
		
		totalCount = hiBoardService.boardListCount(search);
		
		logger.debug("=================");
		logger.debug("totalCount : " + totalCount);
		logger.debug("=================");
		
		if(totalCount > 0) {
			
			paging = new Paging("/board/list", null, totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
			
			paging.addParam("searchType", searchType);
			paging.addParam("searchValue", searchValue);
			paging.addParam("curPage", curPage);
			
			search.setStartRow(paging.getStartRow());
			search.setEndRow(paging.getEndRow());
			
			list = hiBoardService.boardList(search);
			
		}
		
		model.addAttribute("list", list);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("paging", paging);
	
		return "/board/list";
	}
	
	@RequestMapping(value="/board/writeForm", method=RequestMethod.POST)
	public String write(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		User user = null;
		
		if(!StringUtil.isEmpty(cookieUserId)) {
			user = userservice.userSelect(cookieUserId);
			
			model.addAttribute("user", user);
		}
		
		return "/board/writeForm";
	}
}
