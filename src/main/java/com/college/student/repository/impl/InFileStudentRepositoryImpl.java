package com.college.student.repository.impl;

import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.constant.StorageType;
import com.college.student.utils.FileUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
//this is concrete products; this implements the product(StudentRepository) method;
public class InFileStudentRepositoryImpl implements StudentRepository {
    public boolean accept(StorageType storageType){
        return storageType == StorageType.FILE;
    }
    private final FileUtils<Student> fileUtils;
    public InFileStudentRepositoryImpl() {
        File file = new File("C:\\Users\\chakr\\IdeaProjects\\CollegeAdministration\\Student.txt");
        this.fileUtils = new FileUtils<>(file);
    }



    @Override
    public List<Student> listStudents(boolean flag) {
        return null;
    }

    @Override
    public void addStudent(Student student) {
        List<Student> studentList = this.fileUtils.readObject();   //first read the file
        studentList.add(student);  //adding the new student to the existing studentList
        this.fileUtils.writeObject(studentList); //and write the new studentList to the file  else file will be overwritten everytime add a new student;
    }

    @Override
    public Student deleteStudent(int rollNo) {
        List<Student> studentList = this.fileUtils.readObject();  //reading the student file first and
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if(student.getRollNo() == rollNo) {
                iterator.remove();             //after deleting the specific student will add the new list again to the file;
                this.fileUtils.writeObject(studentList);
                return student;
            }
        }
        return null;
    }

    @Override
    public Student updateStudentByRollNo(Student updateStudent) {
        List<Student> studentList = this.fileUtils.readObject();   //first reading the student object file and assign to the student list;
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if(student.getRollNo() == updateStudent.getRollNo()) {
                student.setRollNo(updateStudent.getRollNo());
                student.setName(updateStudent.getName());
                student.setAge(updateStudent.getAge());
                student.setPhoneNo(updateStudent.getPhoneNo());  //after updating all the values will add the new list to the file again;
                student.setGender(updateStudent.getGender());
                this.fileUtils.writeObject(studentList);
                return student;
            }
        }
        return null;
    }

    @Override
    public Student getStudentData(int studentRollNo) {
        Iterator<Student> iterator = this.fileUtils.readObject().iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if(student.getRollNo() == studentRollNo) {
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
        return getStudentData(rollNo) != null;
    }
}
