package com.college.student.repository.impl;

import com.college.student.repository.UserPasswordDao;
import com.college.student.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class UserPasswordDaoImpl implements UserPasswordDao {
    private static final Logger logger = LoggerFactory.getLogger(UserPasswordDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean exists(String userName, String userPassword) {
        String query = "SELECT * FROM USER_PASSWORD WHERE USER_NAME = ? AND USER_PASSWORD = ?"; //change the table name;******
        boolean userExists = false;
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userExists = true;
            }
        } catch (Exception e) {
            logger.error("Error While Checking Username and Password in DB : ", e);
        }
        return userExists;
    }
    public boolean isUserNameExists(String userName) {
        String query = "SELECT * FROM USER_PASSWORD WHERE USER_NAME = ?";
        boolean userExists = false;
        try {
            PreparedStatement preparedStatement = DBConnector.connect().prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userExists = true;
            }
        } catch (Exception e) {
            logger.error("Error While Checking UserName : ", e);
        }
        return userExists;
    }
}
