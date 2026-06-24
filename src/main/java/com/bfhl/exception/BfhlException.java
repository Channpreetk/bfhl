package com.bfhl.exception;

/**
 * Custom exception for BFHL API
 */
public class BfhlException extends RuntimeException {

    private String errorCode;

    public BfhlException(String message) {
        super(message);
        this.errorCode = "BFHL_ERROR";
    }

    public BfhlException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BfhlException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BFHL_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
