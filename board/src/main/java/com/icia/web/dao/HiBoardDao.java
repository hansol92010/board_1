package com.icia.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icia.web.model.HiBoard;
import com.icia.web.model.HiBoardFile;

@Repository("hiBoardDao")
public interface HiBoardDao {
	
	public List<HiBoard> boardList(HiBoard hiBoard);
	
	public int boardListCount(HiBoard hiBoard);
	
	public int boardInsert(HiBoard hiBoard);
	
	public int boardFileInsert(HiBoardFile hiBoardFile);
}
