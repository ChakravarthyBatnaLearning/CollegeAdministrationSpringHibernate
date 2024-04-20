package com.college.student.repository.impl;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRepositoryImpl implements AdmissionRepository {

    private static String SELECT_ADMISSION_QUERY = "SELECT a.admissionID, a.course, a.section, a.admissionYear, a.student.rollNo " +
            "FROM Admission a WHERE a.student.rollNo = :rollNo";
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo)
            throws ServerUnavailableException {
        Session session;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(admission);
            session.flush();
            transaction.commit();
            return true;
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
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Admission> query = session.createQuery(SELECT_ADMISSION_QUERY, Admission.class);
            query.setParameter("rollNo", rollNo);
            admission = query.getSingleResult();
            session.flush();
            transaction.commit();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return admission;
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws ServerUnavailableException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Admission admission = getStudentAdmission(rollNo);
            if (admission == null) return false;
            //    session.delete(admission);
            session.remove(admission);
            session.flush();
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Admission> query = session.createQuery("UPDATE Admission a SET a.course = :newCourse, a.section = :newSection, " +
                    "a.admissionYear = :newAdmissionYear WHERE a.student.rollNo = :rollNo");
            query.setParameter("newCourse", admission.getCourse());
            query.setParameter("newSection", admission.getSection());
            query.setParameter("newAdmissionYear", admission.getAdmissionYear());
            query.setParameter("rollNo", admission.getStudent().getRollNo());
            int rowsAffected = query.executeUpdate();
            if (rowsAffected != 0) return true;
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }
}
