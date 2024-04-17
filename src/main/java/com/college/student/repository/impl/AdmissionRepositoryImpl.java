package com.college.student.repository.impl;

import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.DuplicateAdmissionFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.repository.AdmissionRepository;
import com.college.student.repository.mappers.AdmissionRowMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRepositoryImpl implements AdmissionRepository {
    private static final Logger logger = LoggerFactory.getLogger(AdmissionRepositoryImpl.class);

    private static final String INSERT_QUERY = "INSERT INTO ADMISSION (COURSE, SECTION, ADMISSION_YEAR, ROLL_NO) VALUES (?,?,?,?)";
    private static final String GET_QUERY = "SELECT COURSE, SECTION, ADMISSION_YEAR, ROLL_NO FROM ADMISSION WHERE ROLL_NO = ?";
    private static final String DELETE_QUERY = "DELETE FROM ADMISSION WHERE ROLL_NO = ?";
    private static final String UPDATE_QUERY = "UPDATE ADMISSION SET COURSE = ?, SECTION = ?, ADMISSION_YEAR = ? WHERE ROLL_NO = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addStudentAdmission(Admission admission, int studentRollNo)
            throws ServerUnavailableException, DuplicateAdmissionFoundException {
        int rowsEffected = 0;
        try {
            rowsEffected = jdbcTemplate.update(INSERT_QUERY, admission.getCourse(), admission.getSection(), admission.getAdmissionYear(), studentRollNo);
            if (rowsEffected == 0) {
                logger.error("Student With RollNO : {} Already Exists", studentRollNo);
                throw new DuplicateAdmissionFoundException("Student With ROllNO : " + studentRollNo + " Already Exists", HttpServletResponse.SC_BAD_REQUEST);
            }
            return true;
        } catch (DataAccessException e) {
            logger.error("Error adding student admission: ", e);
            throw new ServerUnavailableException("Error Occurred While Adding Student Data", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public Admission getStudentAdmission(int rollNo) throws ServerUnavailableException {
        Admission admission = null;
        try {
            admission = jdbcTemplate.queryForObject(GET_QUERY, new Object[]{rollNo}, new AdmissionRowMapper());
            if (admission == null) {
                logger.error("Student With RollNo :" + rollNo + " Does Not Have Address");
            }
        } catch (DataAccessException e) {
            logger.error("Error getting student admission: {}", e.getMessage());
            throw new ServerUnavailableException("Error Occurred While Getting Student Data", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return admission;
    }

    @Override
    public boolean deleteStudentAdmission(int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        int rowsEffected = 0;
        try {
            rowsEffected = jdbcTemplate.update(DELETE_QUERY, rollNo);
            if (rowsEffected == 0) {
                logger.error("Admission Record Not Found to Delete having rollNo :{}", rollNo);
                throw new AdmissionRecordNotFoundException("Student With RollNo : + " + rollNo + " DoesNot have Admission Record to Delete", HttpServletResponse.SC_NOT_FOUND);
            }
            return true;
        } catch (DataAccessException e) {
            logger.error("Error deleting student admission: {}", e.getMessage());
            throw new ServerUnavailableException("Error While Deleting Student Admission having RollNO: " + rollNo, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateStudentAdmission(Admission admission, int studentRollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {
        int rowsEffected = 0;
        try {
            rowsEffected = jdbcTemplate.update(UPDATE_QUERY, admission.getCourse(), admission.getSection(),
                    admission.getAdmissionYear(), studentRollNo);
            if (rowsEffected == 0) {
                logger.error("Admission Record Not Found to Update having student rollNo : {}", studentRollNo);
                throw new AdmissionRecordNotFoundException("Student with RollNo : " + studentRollNo + " not Found in Database " +
                        "to Update Admission", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("Error updating student admission: {}", e.getMessage());
            throw new ServerUnavailableException("Server Unavailable Please Try After Some Time", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return true;
    }
}
