package org.example.exceptions;

public class TaskNameDuplicateException extends RuntimeException {
    public TaskNameDuplicateException(String message) {
        super(message);
    }
}
