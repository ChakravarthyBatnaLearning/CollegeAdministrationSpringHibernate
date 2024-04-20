package com.college.student.service;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Student;

import java.util.List;

public interface StudentService {
    public void addStudent(Student student) throws ServerUnavailableException;

    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException;

    public Student deleteStudentByRollNo(int rollNo) throws ServerUnavailableException;

    public Student updateStudentDetailsByRollNo(Student updateStudent) throws ServerUnavailableException;

    public boolean isStudentExist(int rollNo) throws ServerUnavailableException;

    public Student getCompleteStudentData(int studentRollNo) throws ServerUnavailableException;
}
