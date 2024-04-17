package com.college.student.exception;

public class DuplicateAddressFoundException extends Exception{
    private String errorMessage;
    private int errorCode;
    public DuplicateAddressFoundException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
