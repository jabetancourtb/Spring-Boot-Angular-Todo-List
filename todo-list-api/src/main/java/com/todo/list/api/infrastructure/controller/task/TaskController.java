package com.todo.list.api.infrastructure.controller.task;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.list.api.domain.model.task.Task;
import com.todo.list.api.infrastructure.applicationservice.task.TaskApplicationService;
import com.todo.list.api.infrastructure.dto.task.TaskDTO;
import com.todo.list.api.infrastructure.util.ResponseObject;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	private final TaskApplicationService taskService;

	@Autowired
	public TaskController(final TaskApplicationService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping 
	public ResponseEntity<Boolean> create(@RequestBody final TaskDTO taskDto) {
		Boolean taskCreated = taskService.create(taskDto);
		try {
			return new ResponseEntity<>(taskCreated, HttpStatus.CREATED);
		} 
		catch(Exception e) {
			return new ResponseEntity<>(taskCreated, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
	}
	
	@PutMapping
	public ResponseEntity<Boolean> update(@RequestBody final TaskDTO taskDto) {
		Boolean taskUpdated =  taskService.update(taskDto);
		try {
			return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
		} 
		catch(Exception e) {
			return new ResponseEntity<>(taskUpdated, HttpStatus.INTERNAL_SERVER_ERROR); 
		}	
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<Boolean> delete(@PathVariable("id") final long id) {
		Boolean taskDeleted = taskService.deleteById(id);
		try {
			return new ResponseEntity<>(taskDeleted, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(taskDeleted, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/getByUserId/{userId}/{limit}/{itemsToSkip}") 
	public ResponseEntity<ResponseObject<Task>> getByUserId(@PathVariable("userId") final long userId
			, @PathVariable("limit") final long limit
			, @PathVariable("itemsToSkip") final long itemsToSkip) {
		ResponseObject<Task> taskList = taskService.getByUserId(userId, limit, itemsToSkip);
		try {
			return new ResponseEntity<>(taskList, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/getByFilterAndUserId/{limit}/{itemsToSkip}") 
	public ResponseEntity<ResponseObject<Task>> getByFilterAndUserId(@RequestBody final TaskDTO taskDto
			, @PathVariable("limit") final long limit
			, @PathVariable("itemsToSkip") final long itemsToSkip) {
		ResponseObject<Task> taskList = taskService.getByFilterAndUserId(taskDto, limit, itemsToSkip);
		try {
			return new ResponseEntity<>(taskList, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
		}
	}
}
