package com.college.student.exception.handler;


import com.college.student.exception.*;
import com.college.student.pojo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyResponseExceptionHandler.class);

    @ExceptionHandler(value = {StudentNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
        logger.error("Student Not Found Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getClass().getSimpleName() + ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {AdmissionRecordNotFoundException.class})
    public ResponseEntity<Object> handleAdmissionRecordNotFoundException(AdmissionRecordNotFoundException ex, WebRequest request) {
        logger.error("Admission Record Not Found Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(),ex.getClass().getSimpleName() +  ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {AddressRecordNotFoundException.class})
    public ResponseEntity<Object> handleAddressRecordNotFoundException(AddressRecordNotFoundException ex, WebRequest request) {
        logger.error("Address Record Not Found Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getClass().getSimpleName() + ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {DuplicateAdmissionFoundException.class})
    public ResponseEntity<Object> handleDuplicateAdmissionFoundException(DuplicateAdmissionFoundException ex, WebRequest request) {
        logger.error("Duplicate Admission Found Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getClass().getSimpleName() + ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ServerUnavailableException.class})
    public ResponseEntity<Object> handleServerUnavailableException(ServerUnavailableException ex, WebRequest request) {
        logger.error("Server Unavailable Exception: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getClass().getSimpleName() + ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    // Generic exception handler for any other uncaught exceptions

    @ExceptionHandler(value = {DuplicateRollNoFoundException.class})
    public ResponseEntity<Object> handleAddStudentException(DuplicateRollNoFoundException ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Adding Student data : ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getClass().getSimpleName() + ex.getErrorMessage());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {

        logger.error("ControllerAdvice ExceptionHandler catches the Exception: ", ex);
        logger.error("Error While Handling URI : {},", request.getDescription(true), ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getClass().getSimpleName() +  HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        logger.error("Sending ErrorResponse to Client : ", ex);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
