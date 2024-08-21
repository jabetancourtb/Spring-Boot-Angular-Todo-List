package com.todo.list.api.application.dto.task;

import java.sql.Timestamp;

public class TaskDTO {

	private long id;
	private String title;
	private String description;
	private Timestamp date;
	private long userId;
	
	public TaskDTO(final long id, final String name, final String description, final Timestamp date, final long userId) {
		this.id = id;
		this.title = name;
		this.description = description;
		this.date = date;
		this.userId = userId;
	}
	
	public TaskDTO() {
		
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
	
	public Timestamp getDate() {
		return date;
	}

	public long getUserId() {
		return userId;
	}
}
