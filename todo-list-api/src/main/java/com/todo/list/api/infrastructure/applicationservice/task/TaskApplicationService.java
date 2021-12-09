package com.todo.list.api.infrastructure.applicationservice.task;

import com.todo.list.api.domain.model.task.Task;
import com.todo.list.api.infrastructure.dto.task.TaskDTO;
import com.todo.list.api.infrastructure.util.ResponseObject;

public interface TaskApplicationService {

	boolean create(final TaskDTO taskDto);
	
	boolean update(final TaskDTO taskDto);
	
	boolean deleteById(final long id);
	
	ResponseObject<Task> getByUserId(final long userId, final long limit, final long itemsToSkip);
	
	ResponseObject<Task> getByFilterAndUserId(final TaskDTO taskDto, final long limit, final long itemsToSkip);
}
