//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.cache.lru_dll.LRUCache;
import com.college.student.constant.StorageType;
import com.college.student.exception.*;
import com.college.student.pojo.Address;
import com.college.student.pojo.Student;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.AdmissionRepository;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.factory.StudentRepositoryFactory;
import com.college.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

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

    public void addStudent(Student student) throws DuplicateRollNoFoundException, ServerUnavailableException, DuplicateAdmissionFoundException {
        this.studentRepository.addStudent(student);
    }

    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        return this.studentRepository.listStudents(withAssociations);
    }

    public Student deleteStudentByRollNo(int rollNo) throws ServerUnavailableException,
            StudentNotFoundException, AdmissionRecordNotFoundException, AddressRecordNotFoundException {
        Student student = getCompleteStudentData(rollNo);
        if (student == null) return null;
        return this.studentRepository.deleteStudent(rollNo);
    }

    public Student updateStudentDetailsByRollNo(Student updateStudent) throws ServerUnavailableException, StudentNotFoundException, AddressRecordNotFoundException, AdmissionRecordNotFoundException {

        return this.studentRepository.updateStudentByRollNo(updateStudent);
    }


    public boolean isStudentExist(int rollNo) throws ServerUnavailableException, StudentNotFoundException {
        return this.studentRepository.isExist(rollNo);
    }

    @Override
    public Student getCompleteStudentData(int studentRollNo) throws  StudentNotFoundException, ServerUnavailableException {
        return studentRepository.getStudentDataWithAssociations(studentRollNo);
    }
}
