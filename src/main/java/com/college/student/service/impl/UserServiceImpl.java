package com.college.student.service.impl;

import com.college.student.repository.UserPasswordDao;
import com.college.student.repository.impl.UserPasswordDaoImpl;
import com.college.student.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserPasswordDao userPasswordDao;
    public UserServiceImpl(){
        this.userPasswordDao = new UserPasswordDaoImpl();
    }
    @Override
    public boolean authenticateUser(String userName, String userPassword) {
        return this.userPasswordDao.exists(userName, userPassword);
    }
}
