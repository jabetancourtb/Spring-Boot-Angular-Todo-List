package com.todo.list.api.domain.model.user;

public class User {
	
	private final long id;
	private final String email;
	private final String encryptedPassword;
	private final String fullName;
	private final boolean enabled;
	private final String verificationCode;

	
	public User(final long id, final String email, final String encryptedPassword, final String fullName, boolean enabled, String verificationCode) {
		this.id = id;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.fullName = fullName;
		this.enabled = enabled;
		this.verificationCode = verificationCode;
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

	public boolean isEnabled() {
		return enabled;
	}

	public String getVerificationCode() {
		return verificationCode;
	}
}
