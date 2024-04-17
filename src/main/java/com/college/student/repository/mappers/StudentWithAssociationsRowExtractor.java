package com.college.student.repository.mappers;

import com.college.student.pojo.Address;
import com.college.student.pojo.Admission;
import com.college.student.pojo.Student;
import com.college.student.repository.constants.AddressConstants;
import com.college.student.repository.constants.AdmissionConstants;
import com.college.student.repository.constants.StudentConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentWithAssociationsRowExtractor implements ResultSetExtractor<Student> {
    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        List<Address> addresses = new ArrayList<>();
        Admission admission = null;

        while (rs.next()) {
            if (student == null) {
                student = new Student();
                student.setRollNo(rs.getInt(StudentConstants.ROLL_NO.toString()));
                student.setName(rs.getString(StudentConstants.NAME.toString()));
                student.setAge(rs.getByte(StudentConstants.AGE.toString()));
                student.setPhoneNo(rs.getLong(StudentConstants.PHONE_NUMBER.toString()));
                student.setGender(rs.getString(StudentConstants.GENDER.toString()));
            }

            Address address = new Address();
            address.setCountry(rs.getString(AddressConstants.COUNTRY.toString()));
            address.setState(rs.getString(AddressConstants.STATE.toString()));
            address.setCity(rs.getString(AddressConstants.CITY.toString()));
            addresses.add(address);

            if (admission == null) {
                admission = new Admission();
                admission.setCourse(rs.getString(AdmissionConstants.COURSE.toString()));
                admission.setSection(rs.getInt(AdmissionConstants.SECTION.toString()));
                admission.setAdmissionYear(rs.getInt(AdmissionConstants.ADMISSION_YEAR.toString()));
            }
        }

        if (student != null) {
            student.setAddressList(addresses);
            student.setAdmission(admission);
        }

        return student;
    }
}
