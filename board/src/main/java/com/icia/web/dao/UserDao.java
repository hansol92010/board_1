package com.icia.web.dao;

import org.springframework.stereotype.Repository;

import com.icia.web.model.User;

@Repository("userDao")
public interface UserDao {
	
	public User userSelect(String userId);

}
