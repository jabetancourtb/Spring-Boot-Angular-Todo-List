package com.todo.list.api.configuration;

import java.util.ArrayList;

import com.todo.list.api.application.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Autowired 
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		try {
			User user = userRepository.getByEmail(email);

			if(user == null) {
				throw new UsernameNotFoundException("Email " + email + " Not Found");
			}

			if(!user.isEnabled()) {
				throw new BadCredentialsException("User " + user.getFullName() + " is not enabled");
			}
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
		}
		catch(EmptyResultDataAccessException exception) {
			throw new UserNotFoundException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
