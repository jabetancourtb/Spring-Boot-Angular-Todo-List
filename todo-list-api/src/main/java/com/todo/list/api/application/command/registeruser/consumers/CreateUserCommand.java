package com.todo.list.api.application.command.registeruser.consumers;

import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.command.registeruser.ParamsImpl;
import com.todo.list.api.application.command.registeruser.Command;
import com.todo.list.api.application.command.registeruser.Params;
import com.todo.list.api.domain.model.user.User;

public class CreateUserCommand implements Command<User> {

    private UserApplicationService userApplicationService;

    public CreateUserCommand(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Override
    public User execute(Params params) {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return userApplicationService.create(paramsImpl.userDTO);
    }
}
