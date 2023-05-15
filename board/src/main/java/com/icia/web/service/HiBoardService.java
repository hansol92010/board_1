package com.icia.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.web.dao.HiBoardDao;
import com.icia.web.model.HiBoard;

@Service("hiBoardService")
public class HiBoardService {
	
	private Logger logger = LoggerFactory.getLogger(HiBoardService.class);
	
	@Autowired
	private HiBoardDao hiBoardDao;
	
	public List<HiBoard> boardList(HiBoard hiBoard) {
		
		List<HiBoard> list = null;
		
		try {
			list = hiBoardDao.boardList(hiBoard);
		} catch(Exception e) {
			logger.debug("[HiBoardService] boardList Exception", e);
		}
		
		return list;
	}
	
	public int boardListCount(HiBoard hiBoard) {
		int count = 0;
		
		try {
			count = hiBoardDao.boardListCount(hiBoard);
		}
		catch(Exception e) {
			logger.debug("[HiBoardService] boardListCount Exception", e);
		}
		
		return count;
	}

}
