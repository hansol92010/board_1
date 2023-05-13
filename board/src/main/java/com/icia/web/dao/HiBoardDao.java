package com.icia.web.dao;

import org.springframework.stereotype.Repository;

import com.icia.web.model.HiBoard;

@Repository("hiBoardDao")
public interface HiBoardDao {
	
	public HiBoard boardList(HiBoard hiBoard);
}
