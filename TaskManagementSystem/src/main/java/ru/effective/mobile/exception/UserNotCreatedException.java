package ru.effective.mobile.exception;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }

    public UserNotCreatedException(Throwable cause) {
        super(cause);
    }
}
