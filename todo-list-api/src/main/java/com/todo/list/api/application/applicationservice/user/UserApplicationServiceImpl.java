package com.todo.list.api.application.applicationservice.user;

import com.todo.list.api.application.exception.PasswordNotMatchException;
import com.todo.list.api.application.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.domain.model.user.UserRepository;
import com.todo.list.api.application.dto.user.UserDTO;
import com.todo.list.api.infrastructure.util.JwtUtil;

import java.util.UUID;

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
		String randomCode = UUID.randomUUID().toString();
		return userRepository.create(new User(-1, userDto.getEmail(), encryptedPassword, userDto.getFullName(), false, randomCode));
	}

	@Override
	public String login(final String email, final String plainPassword) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    plainPassword
            ));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);

			final User user = userRepository.getByEmail(email);

			return jwtUtil.generateToken(user);
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("Bad credentials", e);
		}
		catch(UserNotFoundException userNotFoundException) {
			throw new UserNotFoundException();
		}
	}

	@Override
	public String update(UserDTO userDto) throws Exception {
		try {
			User user = userRepository.update(new User(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getFullName(), userDto.isEnabled(), userDto.getVerificationCode()));
			return jwtUtil.generateToken(user);
		}
		catch (Exception e) {
			throw new Exception("An exception has occurred ", e);
		}
	}

	@Override
	public Object updatePassword(long userId, String currentPassword, String newPassword) throws Exception {
		String encryptedNewPassword = ENCODER.encode(newPassword);

		final User user = userRepository.getById(userId);

		if(ENCODER.matches(currentPassword, user.getEncryptedPassword())) {
			return userRepository.updatePassword(userId, encryptedNewPassword);
		}
		else {
			throw new PasswordNotMatchException();
		}
	}

	@Override
	public User getUserByVerificationCode(String verificationCode) throws Exception {
		return userRepository.getByVerificationCode(verificationCode);
	}

	@Override
	public User getUserById(long id) throws Exception {
		return userRepository.getById(id);
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		return userRepository.getByEmail(email);
	}

	@Override
	public boolean updateVerificarionCode(long userId) throws Exception {
		String verificationCode = UUID.randomUUID().toString();
		return userRepository.updateVerificationCode(userId, verificationCode);
	}

	@Override
	public boolean updateState(long userId, boolean state) throws Exception {
		return userRepository.updateState(userId, state);
	}

}
