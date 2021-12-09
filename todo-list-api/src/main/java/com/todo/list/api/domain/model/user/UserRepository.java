package com.todo.list.api.domain.model.user;

public interface UserRepository {

	User create(final User user);
	
	User getByEmail(final String email);
}
