package com.college.student.repository.impl;

import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.constant.StorageType;
import com.college.student.utils.CSVReadAndWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InCSVFileStudentRepositoryImpl implements StudentRepository {
    private final String csvFilePath;
    private final File file;
    private final CSVReadAndWriter csvReadAndWriter;

    public boolean accept(StorageType storageType){
        return storageType == StorageType.CSV;
    }
    public InCSVFileStudentRepositoryImpl() {
        csvFilePath = "C:\\Users\\chakr\\IdeaProjects\\CollegeAdministration\\Students.csv";
        file = new File(csvFilePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        csvReadAndWriter = new CSVReadAndWriter(this.file);
    }



    @Override
    public List<Student> listStudents(boolean flag) {
        return null;
    }

    @Override
    public void addStudent(Student student) {
        csvReadAndWriter.writeStudent(student);
    }

    @Override
    public Student deleteStudent(int rollNo) {
        return csvReadAndWriter.deleteStudent(rollNo);
    }

    @Override
    public Student updateStudentByRollNo(Student student) {
        return csvReadAndWriter.updateStudent(student);
    }

    @Override
    public Student getStudentData(int studentRollNo) {
        return csvReadAndWriter.getStudentByRollNo(studentRollNo);
    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) {
        return null;
    }

    @Override
    public boolean isExist(int rollNo) {
       Student student = csvReadAndWriter.getStudentByRollNo(rollNo);
        return student != null;
    }
}
