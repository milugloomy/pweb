package com.baqi.pweb.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baqi.pweb.bean.User;
import com.baqi.pweb.common.BaqiException;
import com.baqi.pweb.dao.UserMapper;

@RestController
public class UserController{
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/login")
	public String login(String username,String password) throws BaqiException{
		User user=userMapper.selectUserByName(username);
		if(user==null)
			throw new BaqiException("username.not.exist");
		if(!user.getPassword().equals(password))
			throw new BaqiException("password.err");
		return null;
	}
	

}
