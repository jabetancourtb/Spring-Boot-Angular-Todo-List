package com.todo.list.api.application.command.recoverypassword.consumers;

import com.todo.list.api.application.applicationservice.userpasswordrecovery.UserPasswordRecoveryService;
import com.todo.list.api.application.command.recoverypassword.Command;
import com.todo.list.api.application.command.recoverypassword.Params;
import com.todo.list.api.application.command.recoverypassword.ParamsImpl;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;

public class CreateUserPasswordRecoveryCommand implements Command<UserPasswordRecovery> {

    UserPasswordRecoveryService userPasswordRecoveryService;

    public CreateUserPasswordRecoveryCommand(UserPasswordRecoveryService userPasswordRecoveryService) {
        this.userPasswordRecoveryService = userPasswordRecoveryService;
    }

    @Override
    public UserPasswordRecovery execute(Params params) throws Exception {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return userPasswordRecoveryService.createUserPasswordRecovery(paramsImpl.userPasswordRecoveryDTO);
    }
}
