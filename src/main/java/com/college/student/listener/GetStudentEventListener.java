package com.college.student.listener;

import com.college.student.event.GetStudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class GetStudentEventListener implements ApplicationListener<GetStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GetStudentEventListener.class);
    @Override
    public void onApplicationEvent(GetStudentEvent getStudentEvent) {
        logger.info("Student Data Received : {}", getStudentEvent.getStudent());
        logger.info("Source : {}", getStudentEvent);
    }
}
