package com.college.student.service.impl;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import com.college.student.service.AdmissionService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository) {
        this.admissionRepository = admissionRepository;
    }

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException {
        return admissionRepository.addStudentAdmission(admission, studentRollNo);
    }

    @Override
    public Admission getStudentAdmission(int rollNo) throws ServerUnavailableException {
        return admissionRepository.getStudentAdmission(rollNo);
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws ServerUnavailableException {
        return admissionRepository.deleteStudentAdmission(rollNo);
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException {
        return admissionRepository.updateStudentAdmission(admission, studentRollNo);
    }
}
