package com.todo.list.api.infrastructure.repository.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.todo.list.api.domain.model.task.Task;
import com.todo.list.api.domain.model.task.TaskRepository;
import com.todo.list.api.infrastructure.util.ResponseObject;

@Repository
public class TaskRepositoryImpl implements TaskRepository{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TaskRepositoryImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean create(Task task) {
		String query = "INSERT INTO Task(title, description, user_id) VALUES(?, ?, ?)";
		int rowsUpdated = jdbcTemplate.update(query, task.getTitle(), task.getDescription(), task.getUserId());
		return rowsUpdated > 0;
	}

	@Override
	public boolean update(Task task) {
		String query = "UPDATE Task SET title = ?, description = ? WHERE id = ?";
		int rowsUpdated = jdbcTemplate.update(query, task.getTitle(), task.getDescription(), task.getId());
		return rowsUpdated > 0;
	}

	@Override
	public boolean deleteById(long id) {
		String query = "DELETE FROM Task Where id = ?";
		int rowsUpdated = jdbcTemplate.update(query, id);
		return rowsUpdated > 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResponseObject<Task> getByUserId(final long userId, final long limit, final long itemsToSkip) {
		String queryContent = "SELECT id, title, description FROM Task WHERE user_id = ? ORDER BY id LIMIT ? OFFSET ?";
		List<Task> tasks = null;
		
		String queryTotalItems = "SELECT COUNT(*) FROM Task WHERE user_id = ?";
		int totalItems = 0;
		
		try {
			tasks = jdbcTemplate.query(queryContent, new Object[] {userId, limit, itemsToSkip}, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String title = resultSet.getString("title");
				final String description = resultSet.getString("description");
				return new Task(id, title, description, userId);
			});
			
			totalItems = jdbcTemplate.queryForObject(queryTotalItems, new Object[]{userId}, Integer.class);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new RuntimeException();
		}
		
		return new ResponseObject<>(tasks, totalItems);
	}

	@Override
	public ResponseObject<Task> getByFilterAndUserId(final Task taskFilter, final long limit, final long itemsToSkip) {
		
		String idFromFilter = null;
		String titleFromFilter = null;
		String descriptionFromFilter = null;
		
		if(taskFilter.getId() > 0){
			idFromFilter = String.valueOf(taskFilter.getId());
		}
		
		if(taskFilter.getTitle() != null) {
			titleFromFilter = "%" + taskFilter.getTitle() + "%";
		}
		
		if(taskFilter.getDescription() != null) {
			descriptionFromFilter = "%" + taskFilter.getDescription() + "%";
		}
	
		MapSqlParameterSource mapParameters = new MapSqlParameterSource();
		
		mapParameters.addValue("id", idFromFilter);
		mapParameters.addValue("title", titleFromFilter);		
		mapParameters.addValue("description",descriptionFromFilter);
		mapParameters.addValue("userId", taskFilter.getUserId());
		mapParameters.addValue("limit", limit);
		mapParameters.addValue("offset", itemsToSkip);
		
		StringBuilder queryContent = new StringBuilder();
		queryContent.append(" SELECT id, title, description ");
		queryContent.append(" FROM Task ");
		queryContent.append(" WHERE user_id = :userId ");
		queryContent.append(" AND ");
		queryContent.append(" ( ");
		queryContent.append(" id = :id ");
		queryContent.append(" OR title LIKE :title " );
		queryContent.append(" OR description LIKE :description ");
		queryContent.append(" ) ");
		queryContent.append(" ORDER BY id LIMIT :limit OFFSET :offset ");
		
		List<Task> tasks = null;
		
		StringBuilder queryTotalItems = new StringBuilder(); 
		queryTotalItems.append(" SELECT COUNT(*) ");
		queryTotalItems.append(" FROM Task ");
		queryTotalItems.append(" WHERE user_id = :userId ");
		queryTotalItems.append(" AND ");
		queryTotalItems.append(" ( ");
		queryTotalItems.append(" id = :id ");
		queryTotalItems.append(" OR title LIKE :title ");
		queryTotalItems.append(" OR description LIKE :description ");
		queryTotalItems.append(" ) ");
		
		int totalItems = 0;
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		
		try {
			tasks = namedParameterJdbcTemplate.query(queryContent.toString(), mapParameters, (resultSet, rowNumber) -> {
				final long id = resultSet.getLong("id");
				final String title = resultSet.getString("title");
				final String description = resultSet.getString("description");
				return new Task(id, title, description, taskFilter.getUserId());
			});
			
			totalItems = namedParameterJdbcTemplate.queryForObject(queryTotalItems.toString(), mapParameters, Integer.class);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new RuntimeException();
		}
		
		return new ResponseObject<>(tasks, totalItems);
	}
}
