package com.todo.list.api.infrastructure.dto.user;

public class UserDTO {
	
	private String email;
	private String password;
	private String fullName;
	
	public UserDTO(final String email, final String password, final String fullName) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}
	
	public UserDTO() {
		
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
}
