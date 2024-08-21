package com.todo.list.api.application.command.recoverypassword.consumers;

import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.command.recoverypassword.Command;
import com.todo.list.api.application.command.recoverypassword.Params;
import com.todo.list.api.application.command.recoverypassword.ParamsImpl;
import com.todo.list.api.application.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateUserCommand implements Command<String> {

    UserApplicationService userApplicationService;

    @Autowired
    public UpdateUserCommand(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Override
    public String execute(Params params) throws Exception {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return userApplicationService.update(paramsImpl.userDTO);
    }
}
