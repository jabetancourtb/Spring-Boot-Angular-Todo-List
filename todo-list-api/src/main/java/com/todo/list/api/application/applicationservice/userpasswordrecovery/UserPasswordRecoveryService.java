package com.todo.list.api.application.applicationservice.userpasswordrecovery;

import com.todo.list.api.application.dto.userpasswordrecovery.UserPasswordRecoveryDTO;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;

public interface UserPasswordRecoveryService {

    UserPasswordRecovery createUserPasswordRecovery(final UserPasswordRecoveryDTO userPasswordRecoveryDto) throws Exception;

    UserPasswordRecovery updateUserPasswordRecovery(final UserPasswordRecoveryDTO userPasswordRecoveryDto) throws Exception;

    UserPasswordRecovery getUserPasswordRecoveryByVerificationCode(String verificationCode) throws Exception;
}
