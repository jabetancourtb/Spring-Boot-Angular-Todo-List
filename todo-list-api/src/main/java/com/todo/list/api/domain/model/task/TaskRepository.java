package com.todo.list.api.domain.model.task;

import com.todo.list.api.infrastructure.util.ResponseObject;

public interface TaskRepository {

	boolean create(final Task task);
	
	boolean update(final Task task);
	
	boolean deleteById(final long id);
	
	ResponseObject<Task> getByUserId(final long userId, final long limit, final long itemsToSkip);
	
	ResponseObject<Task> getByFilterAndUserId(final Task taskFilter, final long limit, final long itemsToSkip);
}
