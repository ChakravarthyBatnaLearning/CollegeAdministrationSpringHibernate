package com.college.student.repository.impl;

import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.constants.AddressType;
import com.college.student.repository.mappers.AddressRowMapper;
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
    private static final String INSERT_QUERY = "INSERT INTO ADDRESS (COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE) VALUES (?,?,?,?,?)";
    private static final String LIST_QUERY = "SELECT COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE FROM ADDRESS WHERE ROLL_NO = ?";
    private static final String UPDATE_ADDRESS_QUERY = "UPDATE ADDRESS SET COUNTRY = ?, STATE = ?, CITY = ?, ADDRESS_TYPE = ? WHERE ROLL_NO = ? AND ADDRESS_TYPE = ?";
    private static final String DELETE_QUERY = "DELETE FROM ADDRESS WHERE ROLL_NO = ?";
    private static final String SELECT_QUERY = "SELECT COUNTRY, STATE, CITY, ROLL_NO, ADDRESS_TYPE FROM ADDRESS WHERE ROLL_NO = ? AND ADDRESS_TYPE = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException {
        int addressRowsEffected = 0;
        try {
            addressRowsEffected = jdbcTemplate.update(INSERT_QUERY, studentAddress.getCountry(), studentAddress.getState(), studentAddress.getCity()
                    , studentAddress.getAddressType(), studentRollNo);

            if (addressRowsEffected == 0) {
                logger.error("Exception While Adding the Student Address {}", studentAddress);
                return false;
            }
        } catch (DataAccessException e) {
            logger.error("Error Occurred while Adding Student Address having RollNo :{}",studentRollNo,e);
            throw new ServerUnavailableException("Error Occurred while Adding Student Address having RollNo : " + studentAddress,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType)
            throws AddressRecordNotFoundException, ServerUnavailableException {
        try {
            int rowsEffected = jdbcTemplate.update(UPDATE_ADDRESS_QUERY, address.getCountry(), address.getState(),
                    address.getCity(), address.getAddressType(), rollNo, addressType.name());
            if (rowsEffected == 0) {
                logger.error("Student Doesn't have Address to Update : {}", address + " To Execute Update Query");
                throw new AddressRecordNotFoundException("Student With RollNo : {}" + rollNo + "Not Found in Database ", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("Error While Updating Student Data having RollNo : {}",rollNo,e);
            throw new ServerUnavailableException("Server Unavailable Please Try After Some Time",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return address;
    }


    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException,AddressRecordNotFoundException{
        int rowsEffected = 0;
        try {
            rowsEffected =  jdbcTemplate.update(DELETE_QUERY, studentRoll);
            if (rowsEffected == 0) {
                throw new AddressRecordNotFoundException("Student with RollNo : " + studentRoll + " Not Found to Delete Address",HttpServletResponse.SC_NOT_FOUND);
            }
            return true;
        } catch (DataAccessException e) {
            logger.error("Error While Deleting Student Data having RollNO : {}",studentRoll,e);
            throw new ServerUnavailableException("Error Occurred While Deleting student Address",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            studentAddressList =  jdbcTemplate.query(LIST_QUERY, new Object[]{studentRollNo}, new AddressRowMapper());
        } catch (DataAccessException e) {
            logger.error("Exception Occurred While Getting Student Address",e);
            throw new ServerUnavailableException("Error While Feteching Student Data",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if (studentAddressList == null) {
            logger.error("Student With RollNO : {} DoesNot Have Address",studentRollNo);
        }
        return studentAddressList;
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException {
        Address address = null;
        try {
            address =  jdbcTemplate.queryForObject(SELECT_QUERY, new Object[]{rollNo, addressType.name()}, new AddressRowMapper());
        } catch (DataAccessException e) {
            logger.error("Error Occurred While Getting Student Address",e);
            throw new ServerUnavailableException("Error Occurred While Getting Student Specific Address",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if (address == null) {
            logger.error("student with rollNo : {} doesn't have addresses",rollNo);
        }
        return address;
    }
}
