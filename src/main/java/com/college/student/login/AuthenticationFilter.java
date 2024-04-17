package com.college.student.login;

import com.college.student.pojo.ErrorResponse;
import com.college.student.utils.CookieHolder;
import com.college.student.utils.HttpUtil;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter implements Filter {    //mapped to StudentServlet.java
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", httpServletRequest);
        logger.info("Value for my_auth_cookie : {}", cookieValue);
        HttpSession userSession = httpServletRequest.getSession(false); //gets userSession if already there
        if (cookieValue != null && CookieHolder.getUserEntity(cookieValue) != null && userSession.getAttribute(cookieValue) != null) {
            logger.info("Authentication Successfully before proceeding chain.doFilter()");
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("After chain.doFilter()");
        } else {
            logger.error("Exception Occurred while Authenticating ");
            ErrorResponse errorResponse = new ErrorResponse(401, "Exception Occurred and Authentication Failed");
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(errorResponse);
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            PrintWriter out = servletResponse.getWriter();
            logger.info("Generated Json Error Response {}", jsonResponse);
            out.println(jsonResponse);
            out.flush();
        }
    }
}
