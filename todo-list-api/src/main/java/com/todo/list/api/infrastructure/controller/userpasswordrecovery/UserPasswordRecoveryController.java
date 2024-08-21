package com.todo.list.api.infrastructure.controller.userpasswordrecovery;

import com.todo.list.api.application.applicationservice.userpasswordrecovery.UserPasswordRecoveryService;
import com.todo.list.api.application.command.recoverypassword.RecoveryPassword;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-password-recovery")
public class UserPasswordRecoveryController {

    private UserPasswordRecoveryService userPasswordRecoveryService;
    private RecoveryPassword recoveryPassword;

    @Autowired
    public void setUserPasswordRecoveryService(UserPasswordRecoveryService userPasswordRecoveryService) {
        this.userPasswordRecoveryService = userPasswordRecoveryService;
    }

    @Autowired
    public void setRecoveryPassword(RecoveryPassword recoveryPassword) {
        this.recoveryPassword = recoveryPassword;
    }


    @GetMapping("/get-user-password-recovery/{verificationCode}")
    public ResponseEntity<?> getUserPasswordRecoveryByVerificationCode(@PathVariable final String verificationCode) {

        try {
            UserPasswordRecovery response = userPasswordRecoveryService.getUserPasswordRecoveryByVerificationCode(verificationCode);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            return new ResponseEntity<>(new Exception("Invalid url"), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/send-recovery-email/{email}")
    public ResponseEntity<?> sendRecoveryEmail(@PathVariable final String email) {

        try {
            recoveryPassword.sendRecoveryEmail(email);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-password/{verificationCode}/{newPassword}")
    public ResponseEntity<?> updatePasswordAndVerificationCode(@PathVariable final String verificationCode, @PathVariable final String newPassword) {

        try {
            recoveryPassword.updatePasswordAndVerificationCode(verificationCode, newPassword);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
