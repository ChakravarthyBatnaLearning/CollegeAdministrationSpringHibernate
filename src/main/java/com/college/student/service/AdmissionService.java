package com.college.student.service;

import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.DuplicateAdmissionFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;

public interface AdmissionService {
    //admission repository method's
    boolean addStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, DuplicateAdmissionFoundException;

    Admission getStudentAdmission(int rollNo) throws ServerUnavailableException;

    boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException;

    boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, AdmissionRecordNotFoundException;

}
