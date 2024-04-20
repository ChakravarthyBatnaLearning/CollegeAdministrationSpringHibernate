package com.college.student.service;

import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.repository.constants.AddressType;
import com.college.student.pojo.Address;

import java.util.List;

public interface AddressService {
    boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException;

    Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) throws ServerUnavailableException;

    boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException;

    boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException;

    List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException;

    Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException;


}
