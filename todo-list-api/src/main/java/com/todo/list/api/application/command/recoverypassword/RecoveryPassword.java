package com.todo.list.api.application.command.recoverypassword;

import com.todo.list.api.application.applicationservice.email.EmailApplicationService;
import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.applicationservice.userpasswordrecovery.UserPasswordRecoveryService;
import com.todo.list.api.application.command.recoverypassword.consumers.*;
import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.application.dto.user.UserDTO;
import com.todo.list.api.application.dto.userpasswordrecovery.UserPasswordRecoveryDTO;
import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecoveryPassword {

    /* PASOS PARA ENVIO DE EMAIL DE RECUPERACIÓN
    * 1. Verificamos que el email ingresado exista (Verificar usuario por email).
    * 2. Si no existe se notifica.
    * 3. Si existe, se obtiene datos del usuario con el correo.
    * 4. Creamos el registro en la tabla password_recovery_url_guid con el id del usuario, también se crea el GUID.
    * 5. Construimos la url de recuperación con el GUID creado.
    * 6. Enviamos el correo con la URL construida.*/


    /* PASOS PARA ABRIR URL DEL CORREO DE RECUPERACIÓN (Front y Back)
    * 1. Obtenemos el GUID de la URL. (Front).
    * 2. Verificamos que el GUID exista en la tabla password_recovery_url_guid.
    * 3. Si no existe el registro, se redirecciona al login. (Back y Front).
    * 4. Si existe, obtenemos el id del usuario a partir del GUID de la tabla password_recovery_url_guid.
    * 5. Consultamos el usuario con el id obtenido.
    * 6. Actualizamos contraseña.
    * 7. Eliminamos el registro de la tabla password_recovery_url_guid, a partir del GUID y id del usuario.*/

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Value("${front-url}")
    private String frontUrl;

    private final UserApplicationService userApplicationService;

    private final UserPasswordRecoveryService userPasswordRecoveryService;

    private final EmailApplicationService emailApplicationService;

    ParamsImpl paramsImpl;

    Command<User> getUserByEmailCommand;

    Command<User> getUserByIdCommand;

    Command<UserPasswordRecovery> createUserPasswordRecoveryCommand;

    Command<Email> sendURLRecoveryPasswordEmailCommand;

    Command<UserPasswordRecovery> getUserPasswordRecoveryByVerificationCodeCommand;

    Command<String> updateUserCommand;

    Command<UserPasswordRecovery> updateUserPasswordRecoveryCommand;


    @Autowired
    public RecoveryPassword(UserApplicationService userApplicationService,
                            UserPasswordRecoveryService userPasswordRecoveryService,
                            EmailApplicationService emailApplicationService) {
        this.userApplicationService = userApplicationService;
        this.userPasswordRecoveryService = userPasswordRecoveryService;
        this.emailApplicationService = emailApplicationService;
        paramsImpl = new ParamsImpl();
        createUserPasswordRecoveryCommand = new CreateUserPasswordRecoveryCommand(this.userPasswordRecoveryService);
        getUserByEmailCommand = new GetUserByEmailCommand(this.userApplicationService);
        getUserByIdCommand = new GetUserByIdCommand(this.userApplicationService);
        sendURLRecoveryPasswordEmailCommand = new SendURLRecoveryPasswordEmailCommand(this.emailApplicationService);
        getUserPasswordRecoveryByVerificationCodeCommand = new GetUserPasswordRecoveryByVerificationCodeCommand(this.userPasswordRecoveryService);
        updateUserCommand = new UpdateUserCommand(this.userApplicationService);
        updateUserPasswordRecoveryCommand = new UpdateUserPasswordRecoveryCommand(this.userPasswordRecoveryService);
    }

    @Transactional
    public void sendRecoveryEmail(String email) throws Exception {
        paramsImpl.email = email;

        paramsImpl.user = getUserByEmailCommand.execute(paramsImpl);

        paramsImpl.userPasswordRecoveryDTO = new UserPasswordRecoveryDTO(-1
                , null
                , paramsImpl.user.getId()
                , null
                , true);

        paramsImpl.userPasswordRecovery = createUserPasswordRecoveryCommand.execute(paramsImpl);

        paramsImpl.emailDTO = new EmailDTO(
                -1
                , "TODO LIST APP"
                , "jabetancourtb@hotmail.com"
                , paramsImpl.user.getEmail()
                , "Activate TODO LIST APP account"
                , setMessageForEmail()
                , null
                , null);

        paramsImpl.emailModel =  sendURLRecoveryPasswordEmailCommand.execute(paramsImpl);
    }

    private String setMessageForEmail() {
        String URLRecovery = frontUrl + "/recovery-password/" + paramsImpl.userPasswordRecovery.getVerificationCode();

        StringBuilder message =  new StringBuilder();
        message.append("Hello " + paramsImpl.user.getFullName());
        message.append(". To change your password, please click on the following link ");
        message.append(URLRecovery);
        return message.toString();
    }

    @Transactional
    public void updatePasswordAndVerificationCode(String verificationCode, String newPassword) throws Exception {
        paramsImpl.verificationCode = verificationCode;

        paramsImpl.userPasswordRecovery = getUserPasswordRecoveryByVerificationCodeCommand.execute(paramsImpl);

        paramsImpl.user = getUserByIdCommand.execute(paramsImpl);

        String encryptedPassword = ENCODER.encode(newPassword);

        paramsImpl.userDTO = new UserDTO(paramsImpl.user.getId()
                , paramsImpl.user.getEmail()
                , encryptedPassword
                , paramsImpl.user.getFullName()
                , paramsImpl.user.isEnabled()
                , paramsImpl.user.getVerificationCode());

        String userToken = updateUserCommand.execute(paramsImpl);

        if(userToken == null) {
            throw new Exception("Failed to update password");
        }


        paramsImpl.userPasswordRecoveryDTO = new UserPasswordRecoveryDTO(paramsImpl.userPasswordRecovery .getId()
                , paramsImpl.userPasswordRecovery.getVerificationCode()
                , paramsImpl.userPasswordRecovery.getUserId()
                , paramsImpl.userPasswordRecovery.getCreationDate()
                , false);

        paramsImpl.userPasswordRecovery = updateUserPasswordRecoveryCommand.execute(paramsImpl);


    }


}
