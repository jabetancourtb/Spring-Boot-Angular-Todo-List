package com.todo.list.api.infrastructure.controller.user;

import java.util.NoSuchElementException;

import com.todo.list.api.application.command.registeruser.RegisterUser;
import com.todo.list.api.application.exception.PasswordNotMatchException;
import com.todo.list.api.application.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.application.applicationservice.user.UserApplicationService;
import com.todo.list.api.application.dto.jwt.JwtDTO;
import com.todo.list.api.application.dto.user.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserApplicationService userService;

	private RegisterUser registerUser;

	@Autowired
	public void setUserService(final UserApplicationService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRegisterUser(RegisterUser registerUser) {
		this.registerUser = registerUser;
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody final UserDTO userDto) {
		User user = null;
		try {
			user = registerUser.register(userDto);
			return new ResponseEntity<>(user, HttpStatus.OK); 
		} 
		catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(noSuchElementException, HttpStatus.NOT_FOUND);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable("email") final String email, @PathVariable("password") final String password) throws Exception {
		String token = null;
		try {
			token = userService.login(email, password);
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK); 
		}
		catch(UserNotFoundException userNotFoundException) {
			return new ResponseEntity<>(userNotFoundException, HttpStatus.BAD_REQUEST);
		}
		catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(noSuchElementException, HttpStatus.NO_CONTENT);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping
	public ResponseEntity<JwtDTO> update(@RequestBody final UserDTO userDto) {
		String token = null;
		try {
			token = userService.update(userDto);
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-password/{userId}/{currentPassword}/{newPassword}")
	public ResponseEntity<?> updatePassword(@PathVariable("userId") final long userId
			, @PathVariable("currentPassword") final String currentPassword
			, @PathVariable("newPassword") final String newPassword) {

		try {
			Boolean passwordUpdated = (Boolean) userService.updatePassword(userId, currentPassword, newPassword);
			return new ResponseEntity<>(passwordUpdated, HttpStatus.OK);
		}
		catch(PasswordNotMatchException passwordNotMatchException) {
			return new ResponseEntity<>("The password entered is not correct", HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-by-verification-code/{verificationCode}")
	public ResponseEntity<?> getUserByVerificationCode(@PathVariable("verificationCode") final String verificationCode) {
		try {
			Object user = userService.getUserByVerificationCode(verificationCode);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-by-email/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") final String email) {
		try {
			Object user = userService.getUserByEmail(email);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-verification-code/{userId}")
	public ResponseEntity<?> updateVerificationCode(@PathVariable("userId") final long userId) {
		try {
			Boolean verificationCodeUpdated = userService.updateVerificarionCode(userId);
			return new ResponseEntity<>(verificationCodeUpdated, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-state/{userId}/{state}")
	public ResponseEntity<?> updateState(@PathVariable("userId") final long userId
			, @PathVariable("state") final boolean state) {
		try {
			Boolean stateUpdated = userService.updateState(userId, state);
			return new ResponseEntity<>(stateUpdated, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
