package com.college.student.repository.impl;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.constants.AddressType;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
    private static String SELECT_QUERY = "SELECT a.addressID, a.country, a.state, a.city,a.addressType " +
            "FROM Address a WHERE a.student.rollNo = :rollNo";
    private static final String SELECT_QUERY_BY_ADDRESS_TYPE = "SELECT a.addressID, a.country, a.state, a.city,a.addressType " +
            "FROM Address a WHERE a.student.rollNo = :rollNo AND a.addressType = :addressType";
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(studentAddress);
            session.flush();
            transaction.commit();
            return true;
        } catch (HibernateException e) {

        }
        return true;
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType)
            throws ServerUnavailableException {
        try {

        } catch (DataAccessException e) {
            logger.error("Error While Updating Student Data having RollNo : {}", rollNo, e);
            throw new ServerUnavailableException("Server Unavailable Please Try After Some Time", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return address;
    }


    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Address> addressList = getStudentAddresses(studentRoll);
            if (addressList != null && !addressList.isEmpty()) {
                for (Address address : addressList) {
                    session.remove(address);
                    // session.delete(address);
                }
            }
            session.flush();
            transaction.commit();
            return true;
        } catch (HibernateException e) {

        }
        return false;
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException {
        return getStudentAddresses(studentRollNo) != null;
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException {
        List<Address> studentAddressList = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Address> query = session.createQuery(SELECT_QUERY, Address.class);
            query.setParameter("rollNo", studentRollNo);
            studentAddressList = query.getResultList();
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {

        }

        return studentAddressList;
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException {
        Address address = null;
        Session session;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query<Address> query = session.createQuery(SELECT_QUERY_BY_ADDRESS_TYPE, Address.class);
            query.setParameter("rollNo", rollNo);
            query.setParameter("addressType", addressType);
            address = query.getSingleResult();
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {

        }

        return address;
    }
}
