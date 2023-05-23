package com.icia.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;

@Repository("hiBoardDao")
public interface HiBoardDao {
	
	public List<HiBoard> boardList(HiBoard hiBoard);
	
	public long boardListCount(HiBoard hiBoard);
	
	public int boardInsert(HiBoard hiBoard);
	
	public int boardFileInsert(HiBoardFile hiBoardFile);
	
	public int readCntIncrease(long hibbsSeq);
	
	public HiBoard boardSelect(long hibbsSeq);
	
	public HiBoardFile boardFileSelect(long hibbsSeq);
	
	public int boardGroupOrderUpdate(HiBoard hiBoard);
	
	public int boardReplyInsert(HiBoard hiBoard);
	
	public int boardDelete(long hibbsSeq);
	
	public int boardFileDelete(long hibbsSeq);
	
	public int boardAnswersCount(long hibbsParent);
}
