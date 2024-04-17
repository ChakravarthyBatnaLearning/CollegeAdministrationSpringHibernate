package com.college.student.pojo;

import java.io.Serializable;

public class UserEntity implements Serializable {
    private String userName;

    public UserEntity(String userName) {
        this.userName = userName;
    }

    public UserEntity() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
