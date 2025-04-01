package org.example.exceptions;

public class TaskNameDublicateException extends RuntimeException {
    public TaskNameDublicateException(String message) {
        super(message);
    }
}
