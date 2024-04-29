//StudentService class will call various method of StudentRepository to perform operations;
package com.college.student.service.impl;

import com.college.student.constant.StorageType;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

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

    @Override
    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        return this.studentRepository.listStudents(withAssociations);
    }

    @Override
    public Student deleteStudentByRollNo(int rollNo) throws ServerUnavailableException {
        Student student = getCompleteStudentData(rollNo);
        if (student == null) return null;
        if (student.getAddressList() != null) {
            logger.info("Executing remove() on addressList");
            this.addressRepository.deleteAllStudentAddresses(rollNo);
        }
        if (student.getAdmission() != null) {
            logger.info("Executing remove() on admission");
            this.admissionRepository.deleteStudentAdmission(rollNo);
        }
        logger.info("Executing remove() on student");
        this.studentRepository.deleteStudent(rollNo);
        return student;
    }

    @Override
    public Student updateStudentDetailsByRollNo(Student updateStudent) throws ServerUnavailableException, StudentNotFoundException {
        return this.studentRepository.updateStudentByRollNo(updateStudent);
    }

    @Override
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
