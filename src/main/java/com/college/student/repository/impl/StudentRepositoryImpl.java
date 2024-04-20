package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.DuplicateRollNoFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.utils.MapObjectToStudent;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);
    private static String LIST_QUERY_WITH_ASOCIATIONS = "SELECT s.rollNo, s.name, s.age, s.phoneNo, s.gender, " +
            "a.country, a.state, a.city, a.addressType, " +
            "ad.course, ad.section, ad.admissionYear " +
            "FROM Student s LEFT JOIN s.addressList a LEFT JOIN s.admission ad";
    private static String LIST_QUERY = "SELECT new com.college.student.pojo.Student(s.rollNo, s.name, s.age, " +
            "s.phoneNo, s.gender) FROM Student s";
    private static String SELECT_QUERY = "SELECT new com.college.student.pojo.Student(s.rollNo, s.name, s.age," +
            " s.phoneNo, s.gender) FROM Student s WHERE s.rollNo = :rollNo";
    @Autowired
    private SessionFactory sessionFactory;

    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        List<Student> studentList = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            if (withAssociations) {

                Query<Object[]> query = session.createQuery(LIST_QUERY_WITH_ASOCIATIONS);
                List<Object[]> resultList = query.getResultList();
                studentList = MapObjectToStudent.mapResultListToStudent(resultList);
            } else {
                Query<Student> query = session.createQuery(LIST_QUERY, Student.class);
                studentList = query.getResultList();

            }
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return studentList;
    }


    @Override
    public void addStudent(Student student) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student deleteStudent(int rollNo) throws  ServerUnavailableException {
        Student student = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            student = getStudentDataWithAssociations(rollNo);
            //   session.delete(student);
            session.remove(student);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error While Deleting Student Data ", e);
            throw new ServerUnavailableException("Error while Deleting Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) throws ServerUnavailableException{
        return null;
    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) throws ServerUnavailableException {
        Student student = null;
        Session session = null;
        Transaction transaction = null;
        try {

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query<Student> studentQuery = session.createQuery(SELECT_QUERY, Student.class);
            studentQuery.setParameter("rollNo", studentRollNo);
            student = studentQuery.getSingleResult();
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error While Retrieving Student Data ", e);
            throw new ServerUnavailableException("Error while Retrieving Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public boolean isExist(int rollNo) throws ServerUnavailableException{
        return getStudentDataWithAssociations(rollNo) != null;
    }
}
