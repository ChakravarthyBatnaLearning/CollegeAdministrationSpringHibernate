package com.college.student.utils;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Getter
@Setter
public class HibernateUtil {

    private static Session session;
    private static SessionFactory sessionFactory;
    private static Transaction transaction;

    public static Session getSession(SessionFactory providedSessionFactory) {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        if (session.isOpen()) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
}
