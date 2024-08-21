package com.todo.list.api.application.command.registeruser;

public interface Command <T> {

    T execute(Params params);
}
