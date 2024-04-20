package com.college.student.repository.impl;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRepositoryImpl implements AdmissionRepository {

    private static final Logger log = LoggerFactory.getLogger(AdmissionRepositoryImpl.class);
    private static final String UPDATE_ADMISSION_QUERY = "UPDATE Admission a SET a.course = :newCourse, a.section = :newSection, " +
            "a.admissionYear = :newAdmissionYear WHERE a.student.rollNo = :rollNo";
    private static final String SELECT_ADMISSION_QUERY = "SELECT a.admissionID, a.course, a.section, a.admissionYear, a.student.rollNo " +
            "FROM Admission a WHERE a.student.rollNo = :rollNo";
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException {
        Session session;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(admission);
            session.flush();
            transaction.commit();
            log.info("Admission added successfully for RollNo: {}", studentRollNo);
            return true;
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error while adding admission for RollNo: {}", studentRollNo, e);
            throw new ServerUnavailableException("Error While Adding the Admission", 500);
        }
    }

    @Override
    public Admission getStudentAdmission(int rollNo) throws ServerUnavailableException {
        Admission admission = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Admission> query = session.createQuery(SELECT_ADMISSION_QUERY, Admission.class);
            query.setParameter("rollNo", rollNo);
            admission = query.getSingleResult();
            session.flush();
            transaction.commit();
        } catch (Throwable e) {
            log.error("Error while retrieving admission for RollNo: {}", rollNo, e);
            throw new ServerUnavailableException("Error While Retrieving the Admission", 500);
        }
        return admission;
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Admission admission = getStudentAdmission(rollNo);
            if (admission == null) return false;
            session.remove(admission);
            session.flush();
            transaction.commit();
            log.info("Admission deleted successfully for RollNo: {}", rollNo);
            return true;
        } catch (HibernateException e) {
            log.error("Error while deleting admission for RollNo: {}", rollNo, e);
            throw new ServerUnavailableException("Error While Deleting the Admission", 500);
        }
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Admission> query = session.createQuery(UPDATE_ADMISSION_QUERY);
            query.setParameter("newCourse", admission.getCourse());
            query.setParameter("newSection", admission.getSection());
            query.setParameter("newAdmissionYear", admission.getAdmissionYear());
            query.setParameter("rollNo", admission.getStudent().getRollNo());
            int rowsAffected = query.executeUpdate();
            session.flush();
            transaction.commit();
            if (rowsAffected != 0) {
                log.info("Admission updated successfully for RollNo: {}", studentRollNo);
                return true;
            }
        } catch (HibernateException e) {
            log.error("Error while updating admission for RollNo: {}", studentRollNo, e);
            throw new ServerUnavailableException("Error While Updating the Admission", 500);
        }
        return false;
    }
}
