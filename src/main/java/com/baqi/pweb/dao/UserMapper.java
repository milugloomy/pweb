package com.baqi.pweb.dao;

import com.baqi.pweb.bean.User;

public interface UserMapper {
    int insert(User user);

    int insertSelective(User user);
    
    User selectUserByName(String username);
}