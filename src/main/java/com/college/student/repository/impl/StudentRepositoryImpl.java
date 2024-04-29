package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.utils.MapObjectToStudent;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);
    private static final String LIST_QUERY_WITH_ASSOCIATIONS = "SELECT s.rollNo, s.name, s.age, s.phoneNo, s.gender, " +
            "a.country, a.state, a.city, a.addressType, " + "ad.course, ad.section, ad.admissionYear " +
            "FROM Student s LEFT JOIN s.addressList a LEFT JOIN s.admission ad";
    private static final String LIST_QUERY_WITHOUT_ASSOCIATIONS = "SELECT new com.college.student.pojo.Student(s.rollNo, s.name, s.age," +
            " s.phoneNo, s.gender) FROM Student s";
    private static final String SELECT_STUDENT_QUERY = "SELECT new com.college.student.pojo.Student(s.rollNo, s.name, s.age, " +
            "s.phoneNo, s.gender) FROM Student s WHERE s.rollNo = :rollNo";
    private static final String UPDATE_STUDENT_QUERY = "UPDATE Student s SET s.name = :name, s.age = :age, s.phoneNo = :phoneNo, s.gender = :gender WHERE s.rollNo = :rollNo";
    @Autowired
    private SessionFactory sessionFactory;

    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        List<Student> studentList = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            if (withAssociations) {

                Query<Object[]> query = session.createQuery(LIST_QUERY_WITH_ASSOCIATIONS);
                List<Object[]> resultList = query.getResultList();
                studentList = MapObjectToStudent.mapResultListToStudent(resultList);
            } else {
                Query<Student> query = session.createQuery(LIST_QUERY_WITHOUT_ASSOCIATIONS, Student.class);
                studentList = query.getResultList();
            }
        } catch (HibernateException e) {
            logger.error("Error while listing students", e);
            throw new ServerUnavailableException("Error while listing students", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return studentList;
    }


    @Override
    public void addStudent(Student student) throws ServerUnavailableException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.persist(student);
        } catch (HibernateException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student deleteStudent(int rollNo) throws ServerUnavailableException {
        Student student = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            student = getStudentDataWithAssociations(rollNo);
            //   session.delete(student);
            logger.info("Executing remove()");
            session.remove(student);
        } catch (HibernateException e) {
            logger.error("Error While Deleting Student Data ", e);
            throw new ServerUnavailableException("Error while Deleting Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) throws ServerUnavailableException, StudentNotFoundException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Query<Student> query = session.createQuery(UPDATE_STUDENT_QUERY);
            query.setParameter("name", student.getName());
            query.setParameter("age", student.getAge());
            query.setParameter("phoneNo", student.getPhoneNo());
            query.setParameter("gender", student.getGender());
            query.setParameter("rollNo", student.getRollNo());
            int rowsAffected = query.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Student With RollNo : " + student.getRollNo() + " Not Found for Update", HttpServletResponse.SC_NOT_FOUND);
            }
            logger.info("Student Updated Successfully : {}", student);
            return student;

        } catch (HibernateException e) {
            logger.error("Error updating student with roll number: {}", student.getRollNo(), e);
            throw new ServerUnavailableException("Error updating student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) throws ServerUnavailableException {
        Student student = null;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Query<Student> studentQuery = session.createQuery(SELECT_STUDENT_QUERY, Student.class);
            studentQuery.setParameter("rollNo", studentRollNo);
            student = studentQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            logger.error("No Student Record Found For Student RollNo : {}", studentRollNo);
        } catch (HibernateException e) {
            logger.error("Error While Retrieving Student Data ", e);
            throw new ServerUnavailableException("Error while Retrieving Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public boolean isExist(int rollNo) throws ServerUnavailableException {
        return getStudentDataWithAssociations(rollNo) != null;
    }
}
