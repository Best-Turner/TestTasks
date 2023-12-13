package ru.effective.mobile.exception;

public class TaskNotCreatedException extends RuntimeException {
    public TaskNotCreatedException(StringBuilder errorMessage) {
    }

    public TaskNotCreatedException(Throwable cause) {
        super(cause);
    }
}
