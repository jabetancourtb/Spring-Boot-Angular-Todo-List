package com.todo.list.api.infrastructure.repository.email;

import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.email.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmailRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Email save(Email email) {
        try {
            String query = "INSERT INTO email(email_from, email_to, owner_ref, send_date_email, status_email, subject, text) VALUES(?, ?, ?, ?, ?, ?, ?)";

            Timestamp timestamp = Timestamp.valueOf(email.getSendDateEmail());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, email.getEmailFrom());
                preparedStatement.setString(2, email.getEmailTo());
                preparedStatement.setString(3, email.getOwnerRef());
                preparedStatement.setTimestamp(4, timestamp);
                preparedStatement.setInt(5, email.getStatusEmail().ordinal());
                preparedStatement.setString(6, email.getSubject());
                preparedStatement.setString(7, email.getText());
                return preparedStatement;
            }, keyHolder);

            int id = (int) keyHolder.getKeys().get("id");

            return new Email(Long.valueOf(id)
                    , email.getOwnerRef()
                    , email.getEmailFrom()
                    , email.getEmailTo()
                    , email.getSubject()
                    , email.getText()
                    , email.getSendDateEmail()
                    , email.getStatusEmail());
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
