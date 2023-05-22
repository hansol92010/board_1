package com.icia.web.controller;

import java.io.File;
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
import org.springframework.web.servlet.ModelAndView;

import com.icia.common.model.FileData;
import com.icia.common.util.FileUtil;
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
import com.icia.web.util.JsonUtil;

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
	
	private static final int LIST_COUNT = 10;	//한 페이지 게시물 수
	private static final int PAGE_COUNT = 10;	//페징 수
	
	@RequestMapping(value="/board/list")
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
	
	@RequestMapping(value="/board/view")
	public String view(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		long curPage = HttpUtil.get(request, "curPage", (long)0);
		
		String boardMe = "N";
		
		HiBoard hiBoard = null;
		
		if(hibbsSeq > 0) {
			
			hiBoard = hiBoardService.boardView(hibbsSeq);
			
			if(hiBoard != null && StringUtil.equals(cookieUserId, hiBoard.getUserId()) ) {
				boardMe = "Y";
			}
				
		}
		
		model.addAttribute("hiBoard", hiBoard);
		model.addAttribute("hibbsSeq", hibbsSeq);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("boardMe", boardMe);
		
		return "/board/view";
	}
	
	@RequestMapping(value="/board/download", method=RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = null;
		
		long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
		
		if(hibbsSeq > 0) {
			HiBoardFile hiBoardFile = hiBoardService.boardFileSelect(hibbsSeq);
		
			if(hiBoardFile != null) {
				File file = new File(UPLOAD_SAVE_DIR + FileUtil.getFileSeparator() + hiBoardFile.getFileName());
				
				logger.debug("##########################################");
				logger.debug("UPLOAD_SAVE_DIR : " + UPLOAD_SAVE_DIR);
				logger.debug("FileUtil.getFileSeparator() : " + FileUtil.getFileSeparator());
				logger.debug("hiBoardFile.getFileName() : " + hiBoardFile.getFileName());
				logger.debug("###########################################");
				
				if(FileUtil.isFile(file)) {
					modelAndView = new ModelAndView();
					
					modelAndView.setViewName("fileDownloadView");
					modelAndView.addObject("file", file);
					modelAndView.addObject("fileName", hiBoardFile.getFileOrgName());
				
					return modelAndView;
				}
				
			}
		
		
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/board/replyForm") 
	public String reply(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		long curPage = HttpUtil.get(request, "curPage", (long)1);
		
		HiBoard hiBoard = null;
		User user = new User();
		
		if(hibbsSeq > 0) {
			
			hiBoard = new HiBoard();
			hiBoard = hiBoardService.boardSelect(hibbsSeq);
			
			if(hiBoard != null) {
				user = userservice.userSelect(cookieUserId);
				
				model.addAttribute("hibbsSeq", hibbsSeq);
				model.addAttribute("hiBoard", hiBoard);
				model.addAttribute("user", user);
				model.addAttribute("searchType", searchType);
				model.addAttribute("searchValue", searchValue);
				model.addAttribute("curPage", curPage);
			}
		}
		
		return "/board/replyForm";
	}
	
	@RequestMapping(value="/board/replyProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> replyProc(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		Response<Object> ajaxRes = new Response<Object>();
		
		String cookieuserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
		String hibbsTitle = HttpUtil.get(request, "hibbsTitle", "");
		String hibbsContent = HttpUtil.get(request, "hibbsContent", "");
		FileData fileData = HttpUtil.getFile(request, "hibbsFile", UPLOAD_SAVE_DIR);
		
		
		if(hibbsSeq > 0 && !StringUtil.isEmpty(hibbsTitle) && !StringUtil.isEmpty(hibbsContent)) {
			
			HiBoard parentHiBoard = hiBoardService.boardSelect(hibbsSeq);
			
			if(parentHiBoard != null) {
				
				HiBoard hiBoard = new HiBoard();
			
				hiBoard.setUserId(cookieuserId);
				hiBoard.setHibbsTitle(hibbsTitle);
				hiBoard.setHibbsContent(hibbsContent);
				hiBoard.setHibbsGroup(parentHiBoard.getHibbsGroup());
				hiBoard.setHibbsIndent(parentHiBoard.getHibbsIndent()+1);
				hiBoard.setHibbsOrder(parentHiBoard.getHibbsOrder()+1);
				hiBoard.setHibbsParent(hibbsSeq);
				
				if(fileData != null && fileData.getFileSize() >= 0) {
					
					HiBoardFile hiBoardFile = new HiBoardFile();
					
					hiBoardFile.setFileName(fileData.getFileName());
					hiBoardFile.setFileOrgName(fileData.getFileOrgName());
					hiBoardFile.setFileExe(fileData.getFileExt());
					hiBoardFile.setFileSize(fileData.getFileSize());
				
					hiBoard.setHiBoardFile(hiBoardFile);
					
				}
				
				try {
					if(hiBoardService.boardreplyInsert(hiBoard) > 0) {
						ajaxRes.setResponse(0, "Success");
					} else {
						ajaxRes.setResponse(500, "Internal Server error");
					}
				}
				catch(Exception e) {
					logger.error("[HiBoardController] /board/replyProc Exception", e);
					ajaxRes.setResponse(500, "Internal Server Error");
				}
			} else {
				ajaxRes.setResponse(404, "Not Fount");
			}
		} else {
			ajaxRes.setResponse(400, "Bad Request");
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("[HiBoardController] /board/replyProc response\n" + JsonUtil.toJsonPretty(ajaxRes));
		}
		
		return ajaxRes;
	}
}
