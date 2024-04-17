package com.college.student.service;

import com.college.student.exception.*;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;

import java.util.List;

public interface StudentService {
    public void addStudent(Student student) throws ServerUnavailableException, DuplicateAdmissionFoundException;

    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException;

    public Student deleteStudentByRollNo(int rollNo) throws ServerUnavailableException, StudentNotFoundException, AdmissionRecordNotFoundException, AddressRecordNotFoundException;

    public Student updateStudentDetailsByRollNo(Student updateStudent) throws ServerUnavailableException, StudentNotFoundException, AddressRecordNotFoundException, AdmissionRecordNotFoundException;

    public Student getStudentByRollNo(int studentRollNo) throws ServerUnavailableException,StudentNotFoundException;

    public boolean isStudentExist(int rollNo) throws ServerUnavailableException, StudentNotFoundException;
    Student getCompleteStudentData(int studentRollNo) throws ServerUnavailableException, StudentNotFoundException;

    // address repository methods


}
