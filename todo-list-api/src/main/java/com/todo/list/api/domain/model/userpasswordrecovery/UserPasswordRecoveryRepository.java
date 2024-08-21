package com.todo.list.api.domain.model.userpasswordrecovery;

public interface UserPasswordRecoveryRepository {

    UserPasswordRecovery createUserPasswordRecovery(final UserPasswordRecovery userPasswordRecovery) throws Exception;

    UserPasswordRecovery updateUserPasswordRecovery(final UserPasswordRecovery userPasswordRecovery) throws Exception;

    UserPasswordRecovery getUserPasswordRecoveryByVerificationCode(String verificationCode) throws Exception;

}
