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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.icia.common.model.FileData;
import com.icia.common.util.StringUtil;
import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;
import com.icia.web.model.Paging;
import com.icia.web.model.Response;
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
	
	@Value("#{env['upload.save.dir']}")
	private String UPLOAD_SAVE_DIR;
	
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
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		
		Paging paging = null;
		HiBoard search = new HiBoard();
		
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
			
			paging = new Paging("/board/list", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
			
			paging.addParam("searchType", searchType);
			paging.addParam("searchValue", searchValue);
			paging.addParam("curPage", curPage);
			
			logger.debug("=================");
			logger.debug("paging.getStartRow() : " + paging.getStartRow());
			logger.debug("paging.getStartPage() : " + paging.getStartPage());
			logger.debug("=================");
			
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
	
	@RequestMapping(value="/board/writeProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> writeProc(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		Response<Object> res = new Response<Object>();
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		String hibbsTitle = HttpUtil.get(request, "hibbsTitle", "");
		String hibbsContent = HttpUtil.get(request, "hibbsContent", "");
		FileData fileData = HttpUtil.getFile(request, "hibbsFile", UPLOAD_SAVE_DIR);
		
		if(!StringUtil.isEmpty(hibbsTitle) && !StringUtil.isEmpty(hibbsContent)) {
			
			HiBoard hiBoard = new HiBoard();
			
			hiBoard.setUserId(cookieUserId);
			hiBoard.setHibbsTitle(hibbsTitle);
			hiBoard.setHibbsContent(hibbsContent);
			
			if(fileData != null && fileData.getFileSize() > 0) {
			
				HiBoardFile hiBoardFile = new HiBoardFile();
				
				hiBoardFile.setFileOrgName(fileData.getFileOrgName());
				hiBoardFile.setFileName(fileData.getFileName());
				hiBoardFile.setFileExe(fileData.getFileExt());
				hiBoardFile.setFileSize(fileData.getFileSize());
				
				hiBoard.setHiBoardFile(hiBoardFile);
				
			}
			
			try {
					if(hiBoardService.boardInsert(hiBoard) > 0) {
						res.setResponse(0, "Success");
					} else {
						res.setResponse(500, "Internal Server Error");
					}
			} catch(Exception e) {
				logger.debug("[HiBoardController] /board/writeProc Exception",e);
			}		
		} else {
			res.setResponse(400, "Bad Request");
		}
		
		return res;
	}
}
