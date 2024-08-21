package com.todo.list.api.application.command.recoverypassword.consumers;

import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.command.recoverypassword.Command;
import com.todo.list.api.application.command.recoverypassword.Params;
import com.todo.list.api.application.command.recoverypassword.ParamsImpl;
import com.todo.list.api.domain.model.user.User;

public class GetUserByIdCommand implements Command<User> {

    UserApplicationService userApplicationService;

    public GetUserByIdCommand(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Override
    public User execute(Params params) throws Exception {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return userApplicationService.getUserById(paramsImpl.userPasswordRecovery.getUserId());
    }
}
