package com.college.student.event;

import com.college.student.pojo.Student;
import org.springframework.context.ApplicationEvent;

public class GetStudentEvent extends ApplicationEvent {
    private final Student student;
    private final Object source;

    public GetStudentEvent(Object source, Student student) {
        super(source);
        this.source = source;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
