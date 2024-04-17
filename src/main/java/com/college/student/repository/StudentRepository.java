package com.college.student.repository;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.constant.StorageType;

import java.util.List;
//this is product interface
public interface StudentRepository {

    List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException;   //display all student details
    void  addStudent(Student student) throws ServerUnavailableException;  //adding student in list;
    Student deleteStudent(int rollNo) throws ServerUnavailableException,StudentNotFoundException;   //deleting specific student from list;
    Student updateStudentByRollNo(Student student) throws ServerUnavailableException,StudentNotFoundException;
    //update specific student by rollNo from list
    Student getStudentData(int studentRollNo) throws ServerUnavailableException, StudentNotFoundException;  //to get specific student data by rollNo;
    Student getStudentDataWithAssociations(int studentRollNo) throws ServerUnavailableException,StudentNotFoundException;
    boolean isExist(int rollNo) throws ServerUnavailableException,StudentNotFoundException;
    public boolean accept(StorageType storageType);
}
