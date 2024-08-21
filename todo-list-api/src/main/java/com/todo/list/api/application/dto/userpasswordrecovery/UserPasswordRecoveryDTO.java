package com.todo.list.api.application.dto.userpasswordrecovery;

import java.sql.Timestamp;

public class UserPasswordRecoveryDTO {

    private final long id;

    private final String verificationCode;

    private final long userId;

    private final Timestamp creationDate;

    private final boolean isValid;

    public UserPasswordRecoveryDTO(long id, String verificationCode, long userId, Timestamp creationDate, boolean isValid) {
        this.id = id;
        this.verificationCode = verificationCode;
        this.userId = userId;
        this.creationDate = creationDate;
        this.isValid = isValid;
    }

    public long getId() {
        return id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public long getUserId() {
        return userId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public boolean isValid() {
        return isValid;
    }

}
