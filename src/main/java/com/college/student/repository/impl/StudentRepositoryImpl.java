package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.DuplicateRollNoFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Address;
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
            String queryStr = "SELECT s.rollNo, s.name, s.age, s.phoneNo, s.gender, " +
                    "a.country, a.state, a.city, a.addressType, " +
                    "ad.course, ad.section, ad.admissionYear " +
                    "FROM Student s LEFT JOIN s.addressList a LEFT JOIN s.admission ad";
            Query<Object[]> query = session.createQuery(queryStr);
            List<Object[]> resultList = query.getResultList();
            studentList = MapObjectToStudent.mapResultListToStudent(resultList);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return studentList;
    }


    @Override
    public void addStudent(Student student) throws DuplicateRollNoFoundException, ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student deleteStudent(int rollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
        } catch (HibernateException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) throws ServerUnavailableException, StudentNotFoundException {

        return null;
    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Create HQL query to fetch student data along with associations
            Query<Object[]> query = session.createQuery(
                    "SELECT s.rollNo, s.name, s.age, s.phoneNo, s.gender, " +
                            "a.country, a.state, a.city, a.addressType, " +
                            "ad.course, ad.section, ad.admissionYear " +
                            "FROM Student s " +
                            "LEFT JOIN s.addressList a " +
                            "LEFT JOIN s.admission ad " +
                            "WHERE s.rollNo = :rollNo", Object[].class);
            query.setParameter("rollNo", studentRollNo);

            // Execute query and retrieve the results
            List<Object[]> resultList = query.getResultList();

            // Commit transaction
            transaction.commit();

            // Check if any results are returned
            if (!resultList.isEmpty()) {
                // Process the first result
                Object[] result = resultList.get(0);
                student = MapObjectToStudent.mapResultToStudent(result);
            } else {
                throw new StudentNotFoundException("Student with roll number " + studentRollNo + " not found.", 500);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error While Retrieving Student Data ", e);
            throw new ServerUnavailableException("Error while Retrieving Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return student;
    }

    @Override
    public boolean isExist(int rollNo) throws ServerUnavailableException, StudentNotFoundException {
        return getStudentDataWithAssociations(rollNo) != null;
    }
}
