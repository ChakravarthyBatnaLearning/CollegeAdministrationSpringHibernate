package com.college.student.repository.impl;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.constants.AddressType;
import jakarta.persistence.NoResultException;
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
public class AddressRepositoryImpl implements AddressRepository {

    private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
    private static final String SELECT_QUERY_BY_ADDRESS_TYPE = "SELECT a.addressID, a.country, a.state, a.city,a.addressType " +
            "FROM Address a WHERE a.student.rollNo = :rollNo AND a.addressType = :addressType";
    private static final String UPDATE_ADDRESS_QUERY = "UPDATE Address a SET a.country = :newCountry, a.state = :newState, " +
            "a.city = :newCity WHERE a.student.rollNo = :rollNo";
    private static final String SELECT_QUERY = "SELECT a.addressID, a.country, a.state, a.city,a.addressType " +
            "FROM Address a WHERE a.student.rollNo = :rollNo";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(studentAddress);
            return true;
        } catch (HibernateException e) {
            logger.error("Error while adding student address for RollNo: {}", studentRollNo, e);
            throw new ServerUnavailableException("Error While Adding the Student Address", 500);
        }
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) throws ServerUnavailableException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Address> addressQuery = session.createQuery(UPDATE_ADDRESS_QUERY);
            addressQuery.setParameter("newCountry", address.getCountry());
            addressQuery.setParameter("newState", address.getState());
            addressQuery.setParameter("newCity", address.getCity());
            addressQuery.setParameter("rollNo", rollNo);
            int rowsEffected = addressQuery.executeUpdate();
            if (rowsEffected != 0) logger.info("Address Updated Successfully {}", address);
        } catch (HibernateException e) {
            logger.error("Error while updating student address for RollNo: {}", rollNo, e);
            throw new ServerUnavailableException("Error While Updating the Student Address", 500);
        }
        return address;
    }


    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            List<Address> addressList = getStudentAddresses(studentRoll);
            if (addressList != null && !addressList.isEmpty()) {
                for (Address address : addressList) {
                    session.remove(address);
                    // session.delete(address);
                }
            }
            logger.debug("All addresses deleted successfully for RollNo: {}", studentRoll);
            return true;
        } catch (HibernateException e) {
            logger.error("Error while deleting all student addresses for RollNo: {}", studentRoll, e);
            throw new ServerUnavailableException("Error While Deleting All Student Addresses", 500);
        }
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException {
        return getStudentAddresses(studentRollNo) != null;
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException {
        List<Address> studentAddressList = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Address> query = session.createQuery(SELECT_QUERY, Address.class);
            query.setParameter("rollNo", studentRollNo);
            studentAddressList = query.getResultList();
        }  catch (NoResultException noResultException) {
            logger.error("No Address Record Found For Student RollNo : {}",studentRollNo);
        }
        catch (HibernateException e) {
            logger.error("Error while retrieving student addresses for RollNo: {}", studentRollNo, e);
            throw new ServerUnavailableException("Error While Retrieving Student Addresses", 500);
        }

        return studentAddressList;
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException {
        Address address = null;
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            Query<Address> query = session.createQuery(SELECT_QUERY_BY_ADDRESS_TYPE, Address.class);
            query.setParameter("rollNo", rollNo);
            query.setParameter("addressType", addressType);
            address = query.getSingleResult();
        } catch (NoResultException noResultException) {
            logger.error("No Address Record Found For Student RollNo : {}", rollNo);
        } catch (HibernateException e) {
            logger.error("Error while retrieving student address for RollNo: {} and AddressType: {}", rollNo, addressType, e);
            throw new ServerUnavailableException("Error While Retrieving Student Address by RollNo and AddressType", 500);
        }
        return address;
    }
}
