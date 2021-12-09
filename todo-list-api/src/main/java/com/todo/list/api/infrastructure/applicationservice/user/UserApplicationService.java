package com.todo.list.api.infrastructure.applicationservice.user;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.infrastructure.dto.user.UserDTO;

public interface UserApplicationService {

	User create(final UserDTO userDto);
	
	String login(final String email, final String plainPassword) throws Exception;
}
