package com.college.student.repository.impl;

import com.college.student.constant.StorageType;
import com.college.student.exception.DuplicateRollNoFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.exception.StudentNotFoundException;
import com.college.student.pojo.Student;
import com.college.student.repository.StudentRepository;
import com.college.student.repository.mappers.ListStudentsWithAssociationsExtractor;
import com.college.student.repository.mappers.StudentRowMapper;
import com.college.student.repository.mappers.StudentWithAssociationsRowExtractor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);

    private static final String LIST_QUERY = "SELECT ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER FROM STUDENT";
    private static final String INSERT_QUERY = "INSERT INTO (ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER) STUDENT VALUES (?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM STUDENT WHERE ROLL_NO = ?";
    private static final String UPDATE_QUERY = "UPDATE STUDENT SET NAME = ?, AGE = ?, PHONE_NUMBER = ?, GENDER = ? WHERE ROLL_NO = ?";
    private static final String GET_QUERY = "SELECT ROLL_NO, NAME, AGE, PHONE_NUMBER, GENDER  FROM student WHERE ROLL_NO = ?";
    private static final String SELECT_STUDENT_WITH_ASSOCIATIONS = "SELECT s.*, a.COUNTRY, a.STATE, a.CITY, admission.COURSE, admission.SECTION, " +
            "admission.ADMISSION_YEAR FROM STUDENT s  LEFT JOIN ADDRESS a ON s.ROLL_NO = a.ROLL_NO LEFT JOIN " +
            "ADMISSION admission ON s.ROLL_NO = admission.ROLL_NO WHERE s.ROLL_NO = ?";
    private static final String SELECT_STUDENTS_WITH_ASSOCIATIONS = "SELECT s.*, a.COUNTRY, a.STATE, a.CITY, admission.COURSE, admission.SECTION, " +
            "admission.ADMISSION_YEAR FROM STUDENT s  LEFT JOIN ADDRESS a ON s.ROLL_NO = a.ROLL_NO LEFT JOIN " +
            "ADMISSION admission ON s.ROLL_NO = admission.ROLL_NO";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public boolean accept(StorageType storageType) {
        return storageType == StorageType.DB;
    }

    @Override
    public List<Student> listStudents(boolean withAssociations) throws ServerUnavailableException {
        List<Student> studentList = null;
        try {
            if (withAssociations) {
                studentList = jdbcTemplate.query(SELECT_STUDENTS_WITH_ASSOCIATIONS, new ListStudentsWithAssociationsExtractor());
            } else {
                studentList = jdbcTemplate.query(LIST_QUERY, new StudentRowMapper());
            }
        } catch (DataAccessException e) {
            logger.error("Error While getting the List of Students", e);
            throw new ServerUnavailableException("Server Unavailable Please Try After Some Time", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return studentList;
    }


    @Override
    public void addStudent(Student student) throws DuplicateRollNoFoundException, ServerUnavailableException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(session);
            transaction.commit();
        } catch (DataAccessException e) {
            logger.error("Error While Adding Student Data ", e);
            throw new ServerUnavailableException("Error while Adding Student", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student deleteStudent(int rollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        int rowsEffected = 0;
        try {
            student = getStudentData(rollNo);
            rowsEffected = jdbcTemplate.update(DELETE_QUERY, rollNo);
            if (rowsEffected == 0) {
                logger.error("Student having rollNo : {} not Found in Database to Delete the Reocrd", rollNo);
                throw new StudentNotFoundException("Student With RollNo : " + rollNo + " Not Found", HttpServletResponse.SC_NOT_FOUND);
            }
            logger.info("Student Successfully Deleted");
        } catch (DataAccessException e) {
            logger.error("Unable to Delete the Student Record", e);
            throw new ServerUnavailableException("Unable to Delete the Student with RollNo : " + rollNo, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public Student updateStudentByRollNo(Student student) throws ServerUnavailableException, StudentNotFoundException {
        try {
            int rowsEffected = jdbcTemplate.update(UPDATE_QUERY, student.getName(), student.getAge(), student.getPhoneNo(), student.getGender(), student.getRollNo());
            if (rowsEffected == 0) {
                logger.error("No Student Found with RollNo : {}", student.getRollNo() + " To Execute Update Query");
                throw new StudentNotFoundException("Student With RollNo : " + student.getRollNo() + " Not Found to Update", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("Error Occurred While Updating the Student ", e);
            throw new ServerUnavailableException("Unable to Update the Student with RollNo : " + student.getRollNo(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public Student getStudentData(int studentRollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        try {
            student = jdbcTemplate.queryForObject(GET_QUERY, new Object[]{studentRollNo}, new StudentRowMapper());
            if (student == null) {
                logger.error("Student With RollNo : {} Not Found", student);
                throw new StudentNotFoundException("Student With RollNo : " + studentRollNo + " Not Found to Retrieve his Address", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("Error While Getting Student with RollNo : {} exception", studentRollNo, e);
            throw new ServerUnavailableException("Error While Getting Student with RollNo :" + studentRollNo, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;

    }

    @Override
    public Student getStudentDataWithAssociations(int studentRollNo) throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        try {
            student = jdbcTemplate.query(SELECT_STUDENT_WITH_ASSOCIATIONS, new Object[]{studentRollNo}, new StudentWithAssociationsRowExtractor());
            if (student == null) {
                logger.error("Student Not Found in database");
                throw new StudentNotFoundException("Student With RollNo : " + studentRollNo + " Not Found", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            logger.error("Error While Getting student info", e);
            throw new ServerUnavailableException("Error While Getting Student Data having RollNo : " + studentRollNo, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return student;
    }

    @Override
    public boolean isExist(int rollNo) throws ServerUnavailableException, StudentNotFoundException {
        return getStudentData(rollNo) != null;
    }
}
