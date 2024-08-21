package com.todo.list.api.application.applicationservice.userpasswordrecovery;

import com.todo.list.api.application.dto.userpasswordrecovery.UserPasswordRecoveryDTO;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPasswordRecoveryServiceImpl implements UserPasswordRecoveryService {

    private final UserPasswordRecoveryRepository userPasswordRecoveryRepository;

    @Autowired
    public UserPasswordRecoveryServiceImpl(final UserPasswordRecoveryRepository userPasswordRecoveryRepository) {
        this.userPasswordRecoveryRepository = userPasswordRecoveryRepository;
    }

    @Override
    public UserPasswordRecovery createUserPasswordRecovery(final UserPasswordRecoveryDTO userPasswordRecoveryDto) throws Exception {
        String verificationCode = UUID.randomUUID().toString();
        UserPasswordRecovery userPasswordRecovery =
                new UserPasswordRecovery(-1
                , verificationCode
                , userPasswordRecoveryDto.getUserId()
                , null
                , true);

        return userPasswordRecoveryRepository.createUserPasswordRecovery(userPasswordRecovery);
    }

    @Override
    public UserPasswordRecovery updateUserPasswordRecovery(final UserPasswordRecoveryDTO userPasswordRecoveryDto) throws Exception {
        UserPasswordRecovery userPasswordRecovery =
                new UserPasswordRecovery(userPasswordRecoveryDto.getId()
                , userPasswordRecoveryDto.getVerificationCode()
                , userPasswordRecoveryDto.getUserId()
                , userPasswordRecoveryDto.getCreationDate()
                , userPasswordRecoveryDto.isValid());

        return userPasswordRecoveryRepository.updateUserPasswordRecovery(userPasswordRecovery);
    }

    @Override
    public UserPasswordRecovery getUserPasswordRecoveryByVerificationCode(String verificationCode) throws Exception {
        return userPasswordRecoveryRepository.getUserPasswordRecoveryByVerificationCode(verificationCode);
    }

}
