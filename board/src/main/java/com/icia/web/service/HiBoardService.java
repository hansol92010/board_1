package com.icia.web.service;

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
	
	public HiBoard boardList(HiBoard hiBoard) {
		
		HiBoard result = null;
		
		try {
			result = hiBoardDao.boardList(hiBoard);
		} catch(Exception e) {
			logger.debug("[HiBoardService] boardList Exception", e);
		}
		
		return result;
	}

}
