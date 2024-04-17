package com.college.student.service.impl;

import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.DuplicateAdmissionFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import com.college.student.service.AdmissionService;

public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository) {
        this.admissionRepository = admissionRepository;
    }

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, DuplicateAdmissionFoundException {
        return admissionRepository.addStudentAdmission(admission, studentRollNo);
    }

    @Override
    public Admission getStudentAdmission(int rollNo) throws ServerUnavailableException {
        return admissionRepository.getStudentAdmission(rollNo);
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        return admissionRepository.deleteStudentAdmission(rollNo);
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException, AdmissionRecordNotFoundException {
        return admissionRepository.updateStudentAdmission(admission, studentRollNo);
    }
}
