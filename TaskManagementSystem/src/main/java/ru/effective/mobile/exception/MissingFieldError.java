package ru.effective.mobile.exception;

public class MissingFieldError extends RuntimeException{
    public MissingFieldError(String message) {
        super(message);
    }
}
