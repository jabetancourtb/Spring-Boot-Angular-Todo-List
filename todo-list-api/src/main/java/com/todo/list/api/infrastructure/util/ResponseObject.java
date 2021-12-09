package com.todo.list.api.infrastructure.util;

import java.util.List;

public class ResponseObject<T> {

	private List<T> content;
	private int totalItems;
	
	public ResponseObject(List<T> content, int totalItems) {
		super();
		this.content = content;
		this.totalItems = totalItems;
	}

	public List<T> getContent() {
		return content;
	}

	public int getTotalItems() {
		return totalItems;
	}
}
