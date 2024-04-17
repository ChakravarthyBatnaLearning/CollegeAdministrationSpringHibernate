package com.college.student.event;

import com.college.student.pojo.Student;
import org.springframework.context.ApplicationEvent;

public class AddStudentEvent extends ApplicationEvent {
    private Object source;
    private Student student;
    public AddStudentEvent(Object source,Student student) {
        super(source);
        this.student = student;
        this.source = source;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
