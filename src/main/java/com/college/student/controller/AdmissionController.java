package com.college.student.controller;

import com.college.student.exception.AdmissionRecordNotFoundException;
import com.college.student.exception.DuplicateAdmissionFoundException;
import com.college.student.exception.ServerUnavailableException;
import com.college.student.pojo.Admission;
import com.college.student.pojo.ErrorResponse;
import com.college.student.service.AdmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class AdmissionController {
    private static final Logger logger = LoggerFactory.getLogger(AdmissionController.class);

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean addStudentAdmission(@RequestBody Admission admission, @PathVariable int studentRollNo) throws ServerUnavailableException, DuplicateAdmissionFoundException {

        logger.info("Request to Add Student Admission Received");
        boolean result = admissionService.addStudentAdmission(admission, studentRollNo);
        logger.info("Successfully Added Student Admission");
        return result;

    }

    // Getting Student Admission
    @GetMapping("/{rollNo}/admission")
    @ResponseBody
    public Admission getStudentAdmission(@PathVariable int rollNo) throws ServerUnavailableException {

        logger.info("Request to Get Student Admission Received");
        Admission admission = admissionService.getStudentAdmission(rollNo);
        logger.info("Successfully Retrieved Student Admission for RollNo : {}", rollNo);
        return admission;

    }

    // Deleting Student Admission
    @DeleteMapping("/{rollNo}/admission")
    @ResponseBody
    public boolean deleteStudentAdmission(@PathVariable int rollNo) throws AdmissionRecordNotFoundException, ServerUnavailableException {

        logger.info("Request to Delete Student Admission Received");
        boolean result = admissionService.deleteStudentAdmission(rollNo);
        logger.info("Successfully Deleted Student Admission for RollNo : {}", rollNo);
        return result;

    }

    // Updating Student Admission
    @PutMapping("/{studentRollNo}/admission")
    @ResponseBody
    public boolean updateStudentAdmission(@RequestBody Admission admission, @PathVariable String studentRollNo) throws ErrorResponse, AdmissionRecordNotFoundException, ServerUnavailableException {

        logger.info("Request to Update Student Admission Received");
        boolean result = admissionService.updateStudentAdmission(admission, Integer.parseInt(studentRollNo));
        logger.info("Successfully Updated Student Admission");
        return result;

    }

}
