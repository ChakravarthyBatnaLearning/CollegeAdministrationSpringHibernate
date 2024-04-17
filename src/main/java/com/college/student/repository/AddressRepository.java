package com.college.student.repository;

import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;

import java.util.List;

public interface AddressRepository {
    boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException;

    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) throws ServerUnavailableException, AddressRecordNotFoundException;

    boolean deleteAllStudentAddresses(int studentRoll) throws AddressRecordNotFoundException,ServerUnavailableException;

    boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException;

    List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException;

    Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException;
}
