package com.icia.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.icia.web.dao.HiBoardDao;
import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;

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
			logger.error("[HiBoardService] boardList Exception", e);
		}
		
		return list;
	}
	
	public int boardListCount(HiBoard hiBoard) {
		int count = 0;
		
		try {
			count = hiBoardDao.boardListCount(hiBoard);
		}
		catch(Exception e) {
			logger.error("[HiBoardService] boardListCount Exception", e);
		}
		
		return count;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int boardInsert(HiBoard hiBoard) throws Exception{
		int count = hiBoardDao.boardInsert(hiBoard);
		
		if(count > 0  && hiBoard.getHiBoardFile() != null) {
			HiBoardFile hiBoardFile = hiBoard.getHiBoardFile();
			hiBoardFile.setHibbsSeq(hiBoard.getHibbsSeq());
			hiBoardFile.setFileSeq((short)1);
			
			hiBoardDao.boardFileInsert(hiBoard.getHiBoardFile());
		}
		
		return count;
	}
	
	public int readCntIncrease(long hibbsSeq) {
		int count = 0;
		
		try {
			count = hiBoardDao.readCntIncrease(hibbsSeq);
		}
		catch(Exception e) {
			logger.error("[HiBoardService] readCntIncrease Exception", e);
		}
		
		return count;
	}
	
	//기본 게시글 조회(보기)
	public HiBoard boardSelect(long hibbsSeq) {
		HiBoard hiBoard = null;
		
		try {
			hiBoard = hiBoardDao.boardSelect(hibbsSeq);
		}
		catch(Exception e) {
			logger.error("[HiBoardService] boardSelect Exception", e);
		}
		
		return hiBoard;
	}
	
	public HiBoard boardView(long hibbsSeq) {
		HiBoard hiBoard = null;
		
		try {
			hiBoard = hiBoardDao.boardSelect(hibbsSeq);
			
			if(hiBoard != null) {
				
				//조회수 증가
				hiBoardDao.readCntIncrease(hibbsSeq);
				
				//첨부파일이 있을 때
				HiBoardFile hiBoardFile = hiBoardDao.boardFileSelect(hibbsSeq);
				
				if(hiBoardFile != null) {
					hiBoard.setHiBoardFile(hiBoardFile);
				}
			}
			
		} catch(Exception e) {
			logger.error("[HiBoardService] boardView Exception",e);
		}
		
		return hiBoard;
	}
	
	public HiBoardFile boardFileSelect(long hibbsSeq) {
		HiBoardFile hiBoardFile = null;
		
		try {
			hiBoardFile = hiBoardDao.boardFileSelect(hibbsSeq);
		}
		catch(Exception e) {
			logger.error("[HiBoardService] boardFileSelect Exception", e);
		}
		
		return hiBoardFile;
	}
	
	public int boardreplyInsert(HiBoard hiBoard) {
	
		int count = 0;

		hiBoardDao.boardGroupOrderUpdate(hiBoard);
		count = hiBoardDao.boardReplyInsert(hiBoard);
		
		if(count > 0 && hiBoard.getHiBoardFile() != null) {
			HiBoardFile hiBoardFile = hiBoard.getHiBoardFile();
			hiBoardFile.setHibbsSeq(hiBoard.getHibbsSeq());
			hiBoardFile.setFileSeq((short)1);
			
			hiBoardDao.boardFileInsert(hiBoard.getHiBoardFile());
			
		}
		return count;
	}

}
