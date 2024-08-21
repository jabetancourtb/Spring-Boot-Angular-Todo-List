package com.todo.list.api.application.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "A registered user with this email was not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
