package com.todo.list.api.application.applicationservice.email;

import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.domain.model.email.Email;

public interface EmailApplicationService {

    Email sendEmail(EmailDTO emailDTO);
}
