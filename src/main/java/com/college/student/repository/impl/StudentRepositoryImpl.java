package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.DuplicateRollNoFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        List<Student> studentList = null;

        return studentList;
    }


    @Override
    public void addStudent(Student student) throws DuplicateRollNoFoundException, ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (DataAccessException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student deleteStudent(int rollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;

        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) throws ServerUnavailableException, StudentNotFoundException {

        return null;
    }

    @Override
    public Student getStudentData(int studentRollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;

        return student;

    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;

        return student;
    }

    @Override
    public boolean isExist(int rollNo) throws ServerUnavailableException, StudentNotFoundException {
        return getStudentData(rollNo) != null;
    }
}
