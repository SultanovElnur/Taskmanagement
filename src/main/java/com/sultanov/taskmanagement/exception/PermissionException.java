package com.sultanov.taskmanagement.exception;

public class PermissionException extends RuntimeException {
    public PermissionException(String message) {
        super(message);
    }
}