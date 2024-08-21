package com.todo.list.api.application.applicationservice.user;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.application.dto.user.UserDTO;

public interface UserApplicationService {

	User create(final UserDTO userDto);
	
	String login(final String email, final String plainPassword) throws Exception;

	String update(final UserDTO userDto) throws Exception;

	Object updatePassword(long userId, String currentPassword, String newPassword) throws Exception;

	User getUserByVerificationCode(String verificationCode) throws Exception;

	User getUserById(long id) throws Exception;

	User getUserByEmail(String email) throws Exception;

	boolean updateVerificarionCode(long userId) throws Exception;

	boolean updateState(long userId, boolean state) throws Exception;

}
