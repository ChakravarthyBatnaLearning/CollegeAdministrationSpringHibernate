package com.college.student.logout;

import com.college.student.pojo.UserEntity;
import com.college.student.utils.CookieHolder;
import com.college.student.utils.HttpUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", request);
        UserEntity userEntity = CookieHolder.removeUser(cookieValue);
        HttpUtil.removeClientCookieFromBrowser("my_auth_cookie", request, response);
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.removeAttribute(cookieValue);
            httpSession.invalidate();
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("LoginPage.html");
    }
}
