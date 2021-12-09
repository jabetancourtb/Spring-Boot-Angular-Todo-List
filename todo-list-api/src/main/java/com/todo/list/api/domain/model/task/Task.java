package com.todo.list.api.domain.model.task;

public class Task {

	private long id;
	private String title;
	private String description;
	private long userId;
	
	public Task(final long id, final String name, final String description, final long userId) {
		this.id = id;
		this.title = name;
		this.description = description;
		this.userId = userId;
	}
	
	public Task() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getUserId() {
		return userId;
	}
}
