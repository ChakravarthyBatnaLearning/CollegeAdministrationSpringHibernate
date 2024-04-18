package com.college.student.repository.impl;

import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.DuplicateAdmissionFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.AdmissionRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRepositoryImpl implements AdmissionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo)
            throws ServerUnavailableException, DuplicateAdmissionFoundException {
        Session session;
        Transaction transaction = null;
        try {
             session = sessionFactory.openSession();
             transaction = session.beginTransaction();
             session.persist(admission);
             transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Admission getStudentAdmission(int rollNo) throws ServerUnavailableException {
        Admission admission = null;
        Session session;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            admission = session.get(Admission.class,rollNo);
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return admission;
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        return false;
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        return false;
    }
}
