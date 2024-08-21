package com.todo.list.api.application.exception;

public class PasswordNotMatchException extends Exception {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "The password entered is not correct";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }

}
