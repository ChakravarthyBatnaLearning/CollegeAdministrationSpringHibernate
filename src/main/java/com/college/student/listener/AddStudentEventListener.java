package com.college.student.listener;

import com.college.student.event.AddStudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class AddStudentEventListener implements ApplicationListener<AddStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AddStudentEventListener.class);


    @Override
    public void onApplicationEvent(AddStudentEvent addStudentEvent) {
        logger.info("New Student Added");
        logger.info("Student Details : {}", addStudentEvent.getStudent());
        logger.info("Source : {}", addStudentEvent.getSource());
    }
}
