package com.todo.list.api.infrastructure.controller.user;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.list.api.domain.model.user.User;
import com.todo.list.api.infrastructure.applicationservice.user.UserApplicationService;
import com.todo.list.api.infrastructure.dto.jwt.JwtDTO;
import com.todo.list.api.infrastructure.dto.user.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserApplicationService userService;

	@Autowired
	public UserController(final UserApplicationService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<User> signUp(@RequestBody final UserDTO userDto) {
		User user = null;
		try {
			user = userService.create(userDto);
			return new ResponseEntity<>(user, HttpStatus.OK); 
		} 
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND); 
		}
	}
	
	@GetMapping("/{email}/{password}") 
	public ResponseEntity<JwtDTO> login(@PathVariable("email") final String email, @PathVariable("password") final String password) throws Exception {
		String token = null;
		try {
			token = userService.login(email, password);
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK); 
		} 
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.UNAUTHORIZED); 
		}
		catch (Exception exception) {
			System.out.println(exception);
			return new ResponseEntity<>(new JwtDTO(token), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
