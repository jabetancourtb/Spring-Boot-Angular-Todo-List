package com.todo.list.api.infrastructure.repository.user;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.user.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepositoryImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public User create(final User user) {
		String query = "INSERT INTO Users(email, encrypted_password, full_name) VALUES(?, ?, ?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getEncryptedPassword());
			preparedStatement.setString(3, user.getFullName());
			return preparedStatement;
		}, keyHolder);
		
		int id = (int) keyHolder.getKeys().get("id");
						
		return new User(Long.valueOf(id)
				, user.getEmail()
				, user.getEncryptedPassword()
				, user.getFullName());
	}

	@SuppressWarnings("deprecation")
	@Override
	public User getByEmail(String email) {
		String query = "SELECT id, email, encrypted_password, full_name FROM Users WHERE email = ?";
		User user = null;
		
		try {
			user = jdbcTemplate.queryForObject(query, new Object[] {email}, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String encryptedPassword = resultSet.getString("encrypted_password");
				final String fullName = resultSet.getString("full_name");
				return new User(id, email, encryptedPassword, fullName);
			});
		}
		catch(EmptyResultDataAccessException exception) {
			throw new RuntimeException();
		}
		
		return user;
	}

}
