package com.college.student.service.impl;

import com.college.student.exception.AddressRecordNotFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Address;
import com.college.student.repository.AddressRepository;
import com.college.student.repository.constants.AddressType;
import com.college.student.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean addStudentAddress(Address studentAddress, int studentRollNo) throws ServerUnavailableException {
        return addressRepository.addStudentAddress(studentAddress, studentRollNo);
    }

    @Override
    public Address updateStudentAddressByRollNo(int rollNo, Address address, AddressType addressType) throws ServerUnavailableException, AddressRecordNotFoundException {
        return addressRepository.updateStudentAddressByRollNo(rollNo, address, addressType);
    }

    @Override
    public boolean deleteAllStudentAddresses(int studentRoll) throws AddressRecordNotFoundException, ServerUnavailableException {
        return addressRepository.deleteAllStudentAddresses(studentRoll);
    }

    @Override
    public boolean isStudentHaveAddress(int studentRollNo) throws ServerUnavailableException {
        return addressRepository.isStudentHaveAddress(studentRollNo);
    }

    @Override
    public List<Address> getStudentAddresses(int studentRollNo) throws ServerUnavailableException {
        return addressRepository.getStudentAddresses(studentRollNo);
    }

    @Override
    public Address getStudentAddressByRollNo(int rollNo, AddressType addressType) throws ServerUnavailableException {
        return addressRepository.getStudentAddressByRollNo(rollNo, addressType);
    }


}
