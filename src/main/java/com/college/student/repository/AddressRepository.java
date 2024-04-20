package com.college.student.repository;

import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Address;
import com.college.student.repository.constants.AddressType;

import java.util.List;

public interface AddressRepository {
    boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException;

    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) throws ServerUnavailableException;

    boolean deleteAllStudentAddresses(int studentRoll) throws ServerUnavailableException;

    boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException;

    List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException;

    Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException;
}
