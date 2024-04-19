package com.college.student.controller;

import com.college.student.event.*;
import com.college.student.exception.*;
import com.college.student.pojo.Student;
import com.college.student.service.StudentService;
import com.college.student.sort.StudentAgeAndGenderComparator;
import com.college.student.utils.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
// ctrl + alt + O to remove unused imports
@Controller

@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    @ResponseBody
    public Student addStudentData(@RequestBody Student student, HttpServletRequest request) throws DuplicateRollNoFoundException, ServerUnavailableException, DuplicateAdmissionFoundException {
        HttpSession userSession = request.getSession(false);
        String cookieValue = HttpUtil.getCookieByName("my_auth_cookie", request);
        if (userSession.getAttribute(cookieValue) != null) {
            logger.info("User Found to be an Admin Request Received to Add Student : {}", student);
            studentService.addStudent(student);
            applicationEventPublisher.publishEvent(new AddStudentEvent(this.getClass(), student));
            logger.info("Added Student to DB");
        }
        return student;
    }

    @GetMapping("/{rollNo}")
    @ResponseBody
    public Student getStudentData(HttpServletRequest request, @PathVariable(value = "rollNo") String rollNo)
            throws StudentNotFoundException, ServerUnavailableException {
        Student student = null;
        logger.info("Request Received to Get the Student Details with RollNo : {} and UserName : {}",
                rollNo, (String) request.getSession(false).getAttribute("username"));

        student = studentService.getCompleteStudentData(Integer.parseInt(rollNo));
        logger.info("Student Details Received : {}", student);
        applicationEventPublisher.publishEvent(new GetStudentEvent(this.getClass(), student));

        return student;

    }

    @GetMapping()
    @ResponseBody
    public List<Student> getStudentList(@RequestParam(value = "withAssociations", required = false, defaultValue = "false") boolean withAssociations)
            throws ServerUnavailableException {
        List<Student> studentList = null;
        logger.info("Request Received to List All Students");
        studentList = studentService.listStudents(withAssociations);
        logger.info("Student List Received : {}", studentList);
        applicationEventPublisher.publishEvent(new GetAllStudentEvent(this.getClass(), studentList));
        studentList.sort(new StudentAgeAndGenderComparator());
        return studentList;
    }

    @PutMapping()
    @ResponseBody
    public Student updateStudentData(@RequestBody Student student) throws StudentNotFoundException, ServerUnavailableException, AdmissionRecordNotFoundException, AddressRecordNotFoundException {
        logger.info("Request to Update the Student : {}", student);
        student = studentService.updateStudentDetailsByRollNo(student);
        logger.info("Request Successfully Completed for Update for Student {}", student);
        applicationEventPublisher.publishEvent(new UpdateStudentEvent(this.getClass(), student));
        //        EventHandler.getInstance(false).publishEvent(new UpdateStudentEvent(this.getClass(), student));
        return student;
    }

    @DeleteMapping("/{rollNo}")
    @ResponseBody
    public String deleteStudentData(@PathVariable String rollNo) throws ServerUnavailableException, StudentNotFoundException, AdmissionRecordNotFoundException, AddressRecordNotFoundException {
        logger.info("Request to Delete Student Successfully Received Student RollNo : {}", Integer.parseInt(rollNo));
        Student student = studentService.deleteStudentByRollNo(Integer.parseInt(rollNo));
        logger.info("Successfully Deleted the Student : {}", student);
        applicationEventPublisher.publishEvent(new DeleteStudentEvent(this.getClass(), student));

        return rollNo;
    }
}