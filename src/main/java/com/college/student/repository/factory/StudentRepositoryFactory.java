package com.college.student.repository.factory;

import com.college.student.repository.StudentRepository;
import com.college.student.constant.StorageType;

import java.util.List;

//this is factory
public class StudentRepositoryFactory {

    private final List<StudentRepository> studentRepositoryList;

    public StudentRepositoryFactory(List<StudentRepository> studentRepositoryList) {
        this.studentRepositoryList = studentRepositoryList;
    }

    public StudentRepository getStudentRepositoryInstance(StorageType storageType) {
        for (StudentRepository studentRepository : studentRepositoryList) {
            if (studentRepository.accept(storageType)) return studentRepository;
        }
        return null;
    }
}
