package com.todo.list.api.application.applicationservice.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.list.api.domain.model.task.Task;
import com.todo.list.api.domain.model.task.TaskRepository;
import com.todo.list.api.application.dto.task.TaskDTO;
import com.todo.list.api.infrastructure.util.ResponseObject;

@Service
public class TaskApplicationServiceImpl implements TaskApplicationService{
	
	private final TaskRepository taskRepository;
	
	@Autowired
	public TaskApplicationServiceImpl(final TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public boolean create(TaskDTO taskDto) {
		return taskRepository.create(new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getDescription(), taskDto.getDate(), taskDto.getUserId()));
	}

	@Override
	public boolean update(TaskDTO taskDto) {
		return taskRepository.update(new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getDescription(), taskDto.getDate(), taskDto.getUserId()));
	}

	@Override
	public boolean deleteById(long id) {
		return taskRepository.deleteById(id);
	}

	@Override
	public ResponseObject<Task> getByUserId(final long userId, final long limit, final long itemsToSkip) {
		return taskRepository.getByUserId(userId, limit, itemsToSkip);
	}
	
	@Override
	public ResponseObject<Task> getByFilterAndUserId(final TaskDTO taskDto, final long limit, final long itemsToSkip) {
		Task task = new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getDescription(), taskDto.getDate(), taskDto.getUserId());
		return taskRepository.getByFilterAndUserId(task, limit, itemsToSkip);
	}
}
