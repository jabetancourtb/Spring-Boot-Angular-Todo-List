package com.todo.list.api.infrastructure.applicationservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.user.UserRepository;
import com.todo.list.api.infrastructure.dto.user.UserDTO;
import com.todo.list.api.infrastructure.util.JwtUtil;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
	
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;
	
	@Autowired
	public UserApplicationServiceImpl(final UserRepository userRepository, final AuthenticationManager authenticationManager
			, final JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;

		this.jwtUtil = jwtUtil;
	}

	@Override
	public User create(final UserDTO userDto) {
		String encryptedPassword = ENCODER.encode(userDto.getPassword());
		return userRepository.create(new User(-1, userDto.getEmail(), encryptedPassword, userDto.getFullName()));
	}

	@Override
	public String login(final String email, final String plainPassword) throws Exception {	
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    plainPassword
            ));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Bad credentials", e);
		}
		
		final User user = userRepository.getByEmail(email);
		
		return jwtUtil.generateToken(user);
	}
}
