package com.todo.list.api.application.command.recoverypassword;

import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.application.dto.user.UserDTO;
import com.todo.list.api.application.dto.userpasswordrecovery.UserPasswordRecoveryDTO;
import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;

public class ParamsImpl implements Params {

    public String email;

    public Email emailModel;

    public EmailDTO emailDTO;


    public User user;

    public UserDTO userDTO;

    public UserPasswordRecovery userPasswordRecovery;

    public UserPasswordRecoveryDTO userPasswordRecoveryDTO;

    public String verificationCode;

    public String newPassword;

}
