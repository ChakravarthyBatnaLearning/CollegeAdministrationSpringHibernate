package com.college.student.repository.mappers;

import com.college.student.repository.constants.AddressConstants;
import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AdmissionConstants;
import com.college.student.repository.constants.StudentConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListStudentsWithAssociationsExtractor implements ResultSetExtractor<List<Student>> {
    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer,Student> studentMap = new HashMap<>();
        Student currentStudent = null;

        while (rs.next()) {
            int rollNo = rs.getInt(StudentConstants.ROLL_NO.toString());

            // If currentStudent is null or the rollNo changes, create a new Student object
            if (currentStudent == null || !(studentMap.containsKey(rollNo))) {
                currentStudent = new Student();
                currentStudent.setRollNo(rollNo);
                currentStudent.setName(rs.getString(StudentConstants.NAME.toString()));
                currentStudent.setAge(rs.getByte(StudentConstants.AGE.toString()));
                currentStudent.setPhoneNo(rs.getLong(StudentConstants.PHONE_NUMBER.toString()));
                currentStudent.setGender(rs.getString(StudentConstants.GENDER.toString()));
                currentStudent.setAddressList(new ArrayList<>()); // Initialize address list
                currentStudent.setAdmission(new Admission()); // Initialize admission
                studentMap.put(currentStudent.getRollNo(),currentStudent);
            }

            // Add address to the current student's address list
            Address address = new Address();
            address.setCountry(rs.getString(AddressConstants.COUNTRY.toString()));
            address.setState(rs.getString(AddressConstants.STATE.toString()));
            address.setCity(rs.getString(AddressConstants.CITY.toString()));
            currentStudent.getAddressList().add(address);

            // Set admission details for the current student
            currentStudent.getAdmission().setCourse(rs.getString(AdmissionConstants.COURSE.toString()));
            currentStudent.getAdmission().setSection(rs.getInt(AdmissionConstants.SECTION.toString()));
            currentStudent.getAdmission().setAdmissionYear(rs.getInt(AdmissionConstants.ADMISSION_YEAR.toString()));
        }

        return new ArrayList<>(studentMap.values());
    }
}
