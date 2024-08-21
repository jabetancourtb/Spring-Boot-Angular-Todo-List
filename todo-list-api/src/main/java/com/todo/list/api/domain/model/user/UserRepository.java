package com.todo.list.api.domain.model.user;

public interface UserRepository {

	User create(final User user);

	User getById(final long userId);
	
	User getByEmail(final String email) throws Exception;

	User update(final User user) throws Exception;

	boolean updatePassword(long userId, String encryptedNewPassword) throws Exception;

	User getByVerificationCode(String verificationCode) throws Exception;

	boolean updateVerificationCode(long userId, String verificationCode) throws Exception;

	boolean updateState(long userId, boolean state) throws Exception;

}
