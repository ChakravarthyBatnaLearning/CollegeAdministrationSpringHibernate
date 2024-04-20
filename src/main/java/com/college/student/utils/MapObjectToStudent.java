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
}
