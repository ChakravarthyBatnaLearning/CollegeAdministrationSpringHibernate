package com.college.student.repository.impl;

import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.constants.AddressType;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);


    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException {

        return true;
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType)
            throws AddressRecordNotFoundException, ServerUnavailableException {
        try {

        } catch (DataAccessException e) {
            logger.error("Error While Updating Student Data having RollNo : {}", rollNo, e);
            throw new ServerUnavailableException("Server Unavailable Please Try After Some Time", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return address;
    }


    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException, AddressRecordNotFoundException {
        return false;
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException {
        return getStudentAddresses(studentRollNo) != null;
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException {
        List<Address> studentAddressList = null;

        return studentAddressList;
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException {
        Address address = null;

        return address;
    }
}
