package ru.effective.mobile.exception;

public class MissingFieldError extends Exception{
    public MissingFieldError(String message) {
        super(message);
    }
}
