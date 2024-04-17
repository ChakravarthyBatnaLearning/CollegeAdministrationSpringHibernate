package com.college.student.repository.mappers;

import com.college.student.pojo.Admission;
import com.college.student.repository.constants.AdmissionConstants;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmissionRowMapper implements RowMapper<Admission> {

    @Override
    public Admission mapRow( ResultSet rs, int rowNum) throws SQLException {
        Admission admission = new Admission();
        admission.setCourse(rs.getString(AdmissionConstants.COURSE.toString()));
        admission.setSection(rs.getInt(AdmissionConstants.SECTION.toString()));
        admission.setAdmissionYear(rs.getInt(AdmissionConstants.ADMISSION_YEAR.toString()));
        admission.setRollNo(rs.getInt(AdmissionConstants.ROLL_NO.toString()));
        return admission;
    }
}
