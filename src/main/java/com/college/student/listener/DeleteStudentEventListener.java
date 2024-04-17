package com.college.student.listener;

import com.college.student.event.DeleteStudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class DeleteStudentEventListener implements ApplicationListener<DeleteStudentEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeleteStudentEventListener.class);


    @Override
    public void onApplicationEvent(DeleteStudentEvent deleteStudentEvent) {
        logger.info("Student with RollNo {}", deleteStudentEvent.getStudent().getRollNo() + " has been Deleted");
        logger.info("Source : {}", deleteStudentEvent.getSource());
    }
}
