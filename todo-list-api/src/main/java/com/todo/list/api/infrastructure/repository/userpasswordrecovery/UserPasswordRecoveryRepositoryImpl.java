package com.todo.list.api.infrastructure.repository.userpasswordrecovery;

import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecovery;
import com.todo.list.api.domain.model.userpasswordrecovery.UserPasswordRecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public class UserPasswordRecoveryRepositoryImpl implements UserPasswordRecoveryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserPasswordRecoveryRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserPasswordRecovery createUserPasswordRecovery(final UserPasswordRecovery userPasswordRecovery) throws Exception {
        String query = "INSERT INTO users_password_recovery(verification_code, user_id, creation_date, is_valid) VALUES(?, ?, ?, ?)";

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userPasswordRecovery.getVerificationCode());
            preparedStatement.setLong(2, userPasswordRecovery.getUserId());
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.setBoolean(4, userPasswordRecovery.isValid());
            return preparedStatement;
        }, keyHolder);

        int id = (int) keyHolder.getKeys().get("id");

        return new UserPasswordRecovery(id,
                userPasswordRecovery.getVerificationCode(),
                userPasswordRecovery.getUserId(),
                timestamp,
                userPasswordRecovery.isValid());
    }


    @Override
    public UserPasswordRecovery updateUserPasswordRecovery(final UserPasswordRecovery userPasswordRecovery) throws Exception {
        String query = "UPDATE users_password_recovery SET verification_code = ?, is_valid = ? WHERE id = ?";

        jdbcTemplate.update(query, userPasswordRecovery.getVerificationCode(), userPasswordRecovery.isValid(), userPasswordRecovery.getId());

        return new UserPasswordRecovery(userPasswordRecovery.getId(),
                userPasswordRecovery.getVerificationCode(),
                userPasswordRecovery.getUserId(),
                userPasswordRecovery.getCreationDate(),
                userPasswordRecovery.isValid());
    }


    @Override
    public UserPasswordRecovery getUserPasswordRecoveryByVerificationCode(String verificationCode) throws Exception {
        String query = "SELECT id, verification_code, user_id, creation_date, is_valid FROM users_password_recovery WHERE verification_code = ?";

        UserPasswordRecovery userPasswordRecovery = null;

        try {
            userPasswordRecovery = jdbcTemplate.queryForObject(query, new Object[]{verificationCode}, (resultSet, rowNumber) -> {
                final long id = resultSet.getLong("id");
                final long userId = resultSet.getLong("user_id");
                final Timestamp creationDate = resultSet.getTimestamp("creation_date");
                final boolean isValid = resultSet.getBoolean("is_valid");
                return new UserPasswordRecovery(id, verificationCode, userId, creationDate, isValid);
            });
        }
        catch(EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(null, 0);
        }
        catch(Exception exception) {
            throw new Exception(exception.getMessage());
        }

        if(!userPasswordRecovery.isValid()) {
            throw new Exception("The URL is not valid");
        }

        return userPasswordRecovery;
    }


}
