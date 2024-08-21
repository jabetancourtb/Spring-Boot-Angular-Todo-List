package com.todo.list.api.application.command.recoverypassword;

public interface Command <T> {
    T execute(Params params) throws Exception;
}
