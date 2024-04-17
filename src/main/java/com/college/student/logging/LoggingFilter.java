package com.college.student.logging;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;


public class LoggingFilter implements Filter {
    private FilterConfig filterConfig;
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String remoteAddr = httpServletRequest.getRemoteAddr();
        String method = httpServletRequest.getMethod();
        String URI = httpServletRequest.getRequestURI();
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long endTime = System.currentTimeMillis();
        long totalTimeTaken = (endTime - startTime);

        //this goes to app.log
        logger.info("Remote Address : {}, Method : {}, URI :{}, TimeTaken : {}", remoteAddr, method, URI, totalTimeTaken);

        //this goes to tomcat logs which is localhost log
        filterConfig.getServletContext().log("Remote Address : " + remoteAddr + "Method : " + method + " URI : " +
                URI + " TimeTaken : " + totalTimeTaken);
    }
}
