package com.todo.list.api.application.command.registeruser;

import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.application.dto.user.UserDTO;
import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.user.User;

public class ParamsImpl implements Params {

    public UserDTO userDTO;

    public User user;

    public EmailDTO emailDTO;

    public Email email;
}
