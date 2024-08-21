package com.todo.list.api.application.command.recoverypassword.consumers;

import com.todo.list.api.application.applicationservice.email.EmailApplicationService;
import com.todo.list.api.application.command.recoverypassword.Command;
import com.todo.list.api.application.command.recoverypassword.Params;
import com.todo.list.api.application.command.recoverypassword.ParamsImpl;

import com.todo.list.api.domain.model.email.Email;

public class SendURLRecoveryPasswordEmailCommand implements Command<Email> {

    EmailApplicationService emailApplicationService;

    public SendURLRecoveryPasswordEmailCommand(EmailApplicationService emailApplicationService) {
        this.emailApplicationService = emailApplicationService;
    }

    @Override
    public Email execute(Params params) throws Exception {
        ParamsImpl paramsImpl = (ParamsImpl) params;
        return emailApplicationService.sendEmail(paramsImpl.emailDTO);
    }
}
