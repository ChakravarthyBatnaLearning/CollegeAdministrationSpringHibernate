//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.cache.lru_dll.LRUCache;
import com.college.student.constant.StorageType;
import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.AdmissionRepository;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.factory.StudentRepositoryFactory;
import com.college.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private static final LRUCache<Integer, Student> studentLRUCache = new LRUCache<>(5);
    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final AdmissionRepository admissionRepository;

    public StudentServiceImpl(StorageType storageType, StudentRepositoryFactory studentRepositoryFactory, AddressRepository addressRepository, AdmissionRepository admissionRepository) {
        this.addressRepository = addressRepository;
        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepositoryFactory.getStudentRepositoryInstance(storageType);
    }

    public void addStudent(Student student) throws ServerUnavailableException {
        this.studentRepository.addStudent(student);
    }

    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        return this.studentRepository.listStudents(withAssociations);
    }

    public Student deleteStudentByRollNo(int rollNo) throws ServerUnavailableException{
        Student student = getCompleteStudentData(rollNo);
        if (student == null) return null;
        if (student.getAddressList() != null) {
            this.addressRepository.deleteAllStudentAddresses(rollNo);
        }
        if (student.getAdmission() != null) {
            this.admissionRepository.deleteStudentAdmission(rollNo);
        }
        this.studentRepository.deleteStudent(rollNo);
        return student;
    }

    public Student updateStudentDetailsByRollNo(Student updateStudent) throws ServerUnavailableException {
        return this.studentRepository.updateStudentByRollNo(updateStudent);
    }


    public boolean isStudentExist(int rollNo) throws ServerUnavailableException {
        return this.studentRepository.isExist(rollNo);
    }

    @Override
    public Student getCompleteStudentData(int studentRollNo) throws ServerUnavailableException {
        Student student = studentRepository.getStudentDataWithAssociations(studentRollNo);
        student.setAddressList(addressRepository.getStudentAddresses(studentRollNo));
        student.setAdmission(admissionRepository.getStudentAdmission(studentRollNo));
        return student;
    }
}
