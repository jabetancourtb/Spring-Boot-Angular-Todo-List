package com.todo.list.api.application.command.registeruser.consumers;

import com.todo.list.api.application.applicationservice.email.EmailApplicationService;
import com.todo.list.api.application.command.registeruser.Command;
import com.todo.list.api.application.command.registeruser.Params;
import com.todo.list.api.application.command.registeruser.ParamsImpl;
import com.todo.list.api.domain.model.email.Email;

public class SendVerificationRegisterEmailCommand implements Command<Email> {

    EmailApplicationService emailApplicationService;

    public SendVerificationRegisterEmailCommand(EmailApplicationService emailApplicationService) {
        this.emailApplicationService = emailApplicationService;
    }

    @Override
    public Email execute(Params params) {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return emailApplicationService.sendEmail(paramsImpl.emailDTO);
    }
}
