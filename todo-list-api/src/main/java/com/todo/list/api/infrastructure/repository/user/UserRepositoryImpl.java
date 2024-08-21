package com.todo.list.api.infrastructure.repository.user;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.todo.list.api.application.exception.UserNotFoundException;
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
		String query = "INSERT INTO Users(email, encrypted_password, full_name, enabled, verification_code) VALUES(?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getEncryptedPassword());
			preparedStatement.setString(3, user.getFullName());
			preparedStatement.setBoolean(4, user.isEnabled());
			preparedStatement.setString(5, user.getVerificationCode());
			return preparedStatement;
		}, keyHolder);

		int id = (int) keyHolder.getKeys().get("id");

		return new User(Long.valueOf(id)
				, user.getEmail()
				, user.getEncryptedPassword()
				, user.getFullName()
				, user.isEnabled()
				, user.getVerificationCode());
	}

	@SuppressWarnings("deprecation")
	@Override
	public User getById(long userId) {
		String query = "SELECT id, email, encrypted_password, full_name, enabled, verification_code FROM Users WHERE id = ?";
		User user = null;

		try {
			user = jdbcTemplate.queryForObject(query, new Object[]{userId}, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String email = resultSet.getString("email");
				final String encryptedPassword = resultSet.getString("encrypted_password");
				final String fullName = resultSet.getString("full_name");
				final boolean enabled = resultSet.getBoolean("enabled");
				final String verificationCode = resultSet.getString("verification_code");
				return new User(id, email, encryptedPassword, fullName, enabled, verificationCode);
			});
		} catch (EmptyResultDataAccessException exception) {
			throw new RuntimeException();
		}

		return user;
	}

	@SuppressWarnings("deprecation")
	@Override
	public User getByEmail(String email) throws Exception {
		String query = "SELECT id, email, encrypted_password, full_name, enabled, verification_code FROM Users WHERE email = ?";
		User user = null;

		try {
			user = jdbcTemplate.queryForObject(query, new Object[]{email}, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String encryptedPassword = resultSet.getString("encrypted_password");
				final String fullName = resultSet.getString("full_name");
				final boolean enabled = resultSet.getBoolean("enabled");
				final String verificationCode = resultSet.getString("verification_code");
				return new User(id, email, encryptedPassword, fullName, enabled, verificationCode);
			});
		} catch (EmptyResultDataAccessException exception) {
			throw new UserNotFoundException();
		}

		return user;
	}

	@Override
	public User update(User user) throws Exception {

		String query = null;

		if(user.getEncryptedPassword() == null) {
			query = "UPDATE Users SET email = ?, full_name = ?, enabled = ?, verification_code = ? WHERE ID = ?";
			jdbcTemplate.update(query, user.getEmail(), user.getFullName(), user.isEnabled(), user.getVerificationCode(), user.getId());
		}
		else {
			query = "UPDATE Users SET email = ?,  encrypted_password = ?, full_name = ?, enabled = ?, verification_code = ? WHERE ID = ?";
			jdbcTemplate.update(query, user.getEmail(), user.getEncryptedPassword(), user.getFullName(), user.isEnabled(), user.getVerificationCode(), user.getId());
		}

		return new User(user.getId(),
				user.getEmail(),
				user.getEncryptedPassword(),
				user.getFullName(),
				user.isEnabled(),
				user.getVerificationCode());
	}

	@Override
	public boolean updatePassword(long userId, String encryptedNewPassword) throws Exception {
		String query = "UPDATE Users SET encrypted_password = ? WHERE ID = ?";
		int rowsUpdated = jdbcTemplate.update(query, encryptedNewPassword, userId);
		return rowsUpdated > 0;
	}

	@Override
	public User getByVerificationCode(String verificationCode) throws Exception {
		String query = "SELECT id, email, encrypted_password, full_name, enabled, verification_code FROM Users WHERE verification_code = ?";
		User user = null;

		try {
			user = jdbcTemplate.queryForObject(query, new Object[]{verificationCode}, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String encryptedPassword = resultSet.getString("encrypted_password");
				final String fullName = resultSet.getString("full_name");
				final boolean enabled = resultSet.getBoolean("enabled");
				final String email = resultSet.getString("email");
				return new User(id, email, encryptedPassword, fullName, enabled, verificationCode);
			});
		} catch (EmptyResultDataAccessException exception) {
			throw new RuntimeException();
		}

		return user;
	}

	@Override
	public boolean updateVerificationCode(long userId, String verificationCode) throws Exception {
		String query = "UPDATE Users SET verification_code = ? WHERE ID = ?";
		int rowsUpdated = jdbcTemplate.update(query, verificationCode, userId);
		return rowsUpdated > 0;
	}

	@Override
	public boolean updateState(long userId, boolean state) throws Exception {
		String query = "UPDATE Users SET enabled = ? WHERE ID = ?";
		int rowsUpdated = jdbcTemplate.update(query, state, userId);
		return rowsUpdated > 0;
	}



}
