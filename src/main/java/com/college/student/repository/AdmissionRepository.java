package com.college.student.repository;

import com.college.student.exception.*;
import com.college.student.pojo.Admission;

public interface AdmissionRepository {
    boolean addStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, DuplicateAdmissionFoundException;

    Admission getStudentAdmission(int rollNo) throws ServerUnavailableException;

    boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException;

    boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, AdmissionRecordNotFoundException;
}
