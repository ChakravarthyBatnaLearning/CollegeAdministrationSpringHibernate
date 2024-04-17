package com.college.student.utils;

import com.college.student.pojo.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class CookieHolder {
    private static final Map<String, UserEntity> userCookies = new HashMap<>();//cookie,userEntity

    public static void addUserName(String cookieValue, UserEntity userEntity) {
        userCookies.put(cookieValue, userEntity);
    }

    public static UserEntity getUserEntity(String cookieValue) {
        return cookieValue != null ? userCookies.get(cookieValue) : null;
    }
    public static UserEntity removeUser(String cookieValue) {
        return userCookies.remove(cookieValue);
    }

}
