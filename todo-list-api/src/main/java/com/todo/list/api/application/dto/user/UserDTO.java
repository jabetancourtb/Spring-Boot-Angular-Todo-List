package com.todo.list.api.application.dto.user;

public class UserDTO {

	private long id;
	private String email;
	private String password;
	private String fullName;
	private boolean enabled;
	private String verificationCode;
	
	public UserDTO(final long id, final String email, final String password, final String fullName, final boolean enabled, final String verificationCode) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.enabled = enabled;
		this.verificationCode = verificationCode;
	}
	
	public UserDTO() {
		
	}

	public long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
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
