package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class InMemoryStudentRepositoryImpl implements StudentRepository {
    public boolean accept(StorageType storageType){
        return storageType == StorageType.IN_MEMORY;
    }
    private final List<Student> studentList = new ArrayList<>();

    @Override
    public List<Student> listStudents(boolean flag) {
        return null;
    }

    @Override
    public void addStudent(Student student) {  //adding student in list;
        studentList.add(student);
    }


    public List<Student> listStudents() {
        return this.studentList;
    }  //printing/retrieving all the student's from list;
    @Override
    public Student deleteStudent(int rollNo) {
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();       //deleting specific student details from list;
            if(student.getRollNo() == rollNo) {
                iterator.remove();
                return student;
            }
        }
        return null;
    }

    @Override
    public Student updateStudentByRollNo(Student updateStudent) {
        for (Student student : studentList) {
            if (student.getRollNo() == updateStudent.getRollNo()) {
                student.setRollNo(updateStudent.getRollNo());
                student.setName(updateStudent.getName());
                student.setAge(updateStudent.getAge());
                student.setPhoneNo(updateStudent.getPhoneNo());
                student.setGender(updateStudent.getGender());
                return student;
            }
        }
        return null;
    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) {
        return null;
    }

    @Override
    public boolean isExist(int rollNo) {
        for( Student student : studentList) {   //returns true if student rollNo exists else false;
            if(student.getRollNo() == rollNo) {
                return true;
            }
        }
        return false;
    }

}
