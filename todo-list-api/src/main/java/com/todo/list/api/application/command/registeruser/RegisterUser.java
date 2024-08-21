package com.todo.list.api.application.command.registeruser;

import com.todo.list.api.application.applicationservice.email.EmailApplicationService;
import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.command.registeruser.consumers.CreateUserCommand;
import com.todo.list.api.application.command.registeruser.consumers.SendVerificationRegisterEmailCommand;
import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.application.dto.user.UserDTO;

import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegisterUser {

    @Value("${front-url}")
    private String frontUrl;
    private final UserApplicationService userApplicationService;
    private final EmailApplicationService emailApplicationService;
    ParamsImpl paramsImpl;
    Command<User> createUserCommand;
    Command<Email> sendVerificationRegisterEmailCommand;

    @Autowired
    public RegisterUser(UserApplicationService userApplicationService, EmailApplicationService emailApplicationService) {
    	this.userApplicationService = userApplicationService;
    	this.emailApplicationService = emailApplicationService;
    	paramsImpl = new ParamsImpl();
        createUserCommand = new CreateUserCommand(this.userApplicationService);
        sendVerificationRegisterEmailCommand = new SendVerificationRegisterEmailCommand(this.emailApplicationService);
    }

    @Transactional
	public User register(UserDTO userDTO) {
		
        paramsImpl.userDTO = userDTO;

        paramsImpl.user = createUserCommand.execute(paramsImpl);

        BeanUtils.copyProperties(paramsImpl.user, paramsImpl.userDTO);

        EmailDTO emailDTO = new EmailDTO(
                -1
                , "TODO LIST APP"
                , "jabetancourtb@hotmail.com"
                , userDTO.getEmail()
                , "Activate TODO LIST APP account"
                , setMessageForEmail(paramsImpl.user)
                , null
                , null);

        paramsImpl.emailDTO = emailDTO;

        paramsImpl.email =  sendVerificationRegisterEmailCommand.execute(paramsImpl);

        return paramsImpl.user;
    }

    private String setMessageForEmail(User user) {
        String URLConfirm = frontUrl + "/confirm-sign-up/" + user.getVerificationCode();

        StringBuilder message =  new StringBuilder();
        message.append("Hello " + user.getFullName());
        message.append(". To confirm the registration, please click on the following link ");
        message.append(URLConfirm);
        return message.toString();
    }

}
