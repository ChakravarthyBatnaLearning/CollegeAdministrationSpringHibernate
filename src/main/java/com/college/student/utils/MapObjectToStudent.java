package com.college.student.utils;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AddressType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapObjectToStudent {
    public static List<Student> mapResultListToStudent(List<Object[]> resultList) {
        Map<Integer, Student> studentMap = new HashMap<>();
        for (Object[] row : resultList) {
            int rollNo = (int) row[0];
            Student student = studentMap.getOrDefault(rollNo, new Student());
            if (student.getRollNo() == 0) {
                student.setRollNo(rollNo);
                student.setName((String) row[1]);
                student.setAge((byte) row[2]);
                student.setPhoneNo((long) row[3]);
                student.setGender((String) row[4]);
            }

            if (row[5] != null && row[6] != null && row[7] != null && row[8] != null) {
                Address address = new Address();
                address.setCountry((String) row[5]);
                address.setState((String) row[6]);
                address.setCity((String) row[7]);
                address.setAddressType((AddressType) row[8]);

                student.getAddressList().add(address);
            }

            if (row[9] != null && row[10] != null && row[11] != null) {
                Admission admission = new Admission();
                admission.setCourse((String) row[9]);
                admission.setSection((int) row[10]);
                admission.setAdmissionYear((int) row[11]);
                student.setAdmission(admission);

            }

            studentMap.put(rollNo, student);
        }
        return new ArrayList<>(studentMap.values());
    }
    public static Student mapResultToStudent(Object[] result) {
        int rollNo = (int) result[0];
        String name = (String) result[1];
        byte age = (byte) result[2];
        long phoneNo = (long) result[3];
        String gender = (String) result[4];

        Student student = new Student();
        student.setRollNo(rollNo);
        student.setName(name);
        student.setAge(age);
        student.setPhoneNo(phoneNo);
        student.setGender(gender);
        if (student.getAddressList() == null) {
            student.setAddressList(new ArrayList<>());
        }

        if (result[5] != null && result[6] != null && result[7] != null && result[8] != null) {
            // Set address fields to the student's address list
            Address address = new Address();
            address.setCountry((String) result[5]);
            address.setState((String) result[6]);
            address.setCity((String) result[7]);
            address.setAddressType((AddressType) result[8]);
            student.getAddressList().add(address);
        }

        // Check for null values before accessing elements of result
        if (result[9] != null && result[10] != null && result[11] != null) {
            // Set admission fields to the student's admission
            Admission admission = new Admission();
            admission.setCourse((String) result[9]);
            admission.setSection((int) result[10]);
            admission.setAdmissionYear((int) result[11]);
            student.setAdmission(admission);
        }
        return student;
    }
}
