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
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
        try {
            session = sessionFactory.openSession();
            Query<Admission> query = session.createQuery("SELECT a.admissionID, a.course, a.section, a.admissionYear " +
                    "FROM Admission", Admission.class);

            query.setParameter("rollNo", rollNo);
            admission = query.getSingleResult();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return admission;
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Query<Admission> query = session.createQuery("DELETE FROM Admission", Admission.class);
            query.setParameter("rollNo",rollNo);
            int rowsEffected = query.executeUpdate();
            if (rowsEffected != 0) return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
         try {
             Session session = sessionFactory.openSession();
//             Query query = session.createQuery("UPDATE Admission a SET a.course = :newCourse, a.section = :newSection, " +
//                     "a.admissionYear = :newAdmissionYear WHERE a.student.rollNo = :rollNo");
//             query.setParameter("newCourse", admission.getCourse());
//             query.setParameter("newSection", admission.getSection());
//             query.setParameter("newAdmissionYear", admission.getAdmissionYear());
//             query.setParameter("rollNo", admission.getStudent().getRollNo());
//             int rowsAffected = query.executeUpdate();
//             if (rowsAffected != 0) return true;

         } catch (HibernateException e) {
             e.printStackTrace();
         }
         return false;
    }
}
