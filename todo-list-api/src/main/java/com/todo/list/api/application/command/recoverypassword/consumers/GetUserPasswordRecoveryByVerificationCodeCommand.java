package com.todo.list.api.application.command.recoverypassword.consumers;

import com.todo.list.api.application.applicationservice.userpasswordrecovery.UserPasswordRecoveryService;
import com.todo.list.api.application.command.recoverypassword.Command;
import com.todo.list.api.application.command.recoverypassword.Params;
import com.todo.list.api.application.command.recoverypassword.ParamsImpl;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;
import org.springframework.beans.factory.annotation.Autowired;

public class GetUserPasswordRecoveryByVerificationCodeCommand implements Command<UserPasswordRecovery> {

    UserPasswordRecoveryService userPasswordRecoveryService;

    @Autowired
    public GetUserPasswordRecoveryByVerificationCodeCommand(UserPasswordRecoveryService userPasswordRecoveryService) {
        this.userPasswordRecoveryService = userPasswordRecoveryService;
    }

    @Override
    public UserPasswordRecovery execute(Params params) throws Exception {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return userPasswordRecoveryService.getUserPasswordRecoveryByVerificationCode(paramsImpl.verificationCode);
    }
}
