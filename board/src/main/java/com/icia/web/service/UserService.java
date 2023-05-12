package com.icia.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.web.dao.UserDao;
import com.icia.web.model.User;

@Service("userService")
public class UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	public User userSelect(String userId) {
		
		User user = null;
		
		try {
			user = userDao.userSelect(userId);
		} catch(Exception e) {
			logger.debug("UserService userSelect Exception", e);
		}
		
		return user;
	}
	
	public int userInsert(User user) {
		int count = 0;
		
		try {
			count = userDao.userInsert(user);
		} catch(Exception e) {
			logger.debug("UserService userInsert Exception", e);
		}
		
		return count;
	}
	
	public int userUpdate(User user) {
		int count = 0;
		
		try {
			count = userDao.userUpdate(user);
		}
		catch(Exception e) {
			logger.debug("UserService userUpdate Exception", e);
		}
		
		return count;
	}

}
