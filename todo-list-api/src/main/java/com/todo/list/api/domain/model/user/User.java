package com.todo.list.api.domain.model.user;

public class User {
	
	private final long id;
	private final String email;
	private final String encryptedPassword;
	private final String fullName;
	
	public User(final long id, final String email, final String encryptedPassword, final String fullName) {
		this.id = id;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.fullName = fullName;
	}
	
	public long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	
	public String getFullName() {
		return fullName;
	}
}
