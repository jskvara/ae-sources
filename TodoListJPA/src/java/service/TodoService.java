package service;

import com.google.inject.Inject;
import dao.TodoDAO;
import entity.TodoEntity;
import java.util.Collection;
import java.util.Date;

public class TodoService {

	@Inject
	TodoDAO todoDAO;

	public Collection<TodoEntity> getAllTodos() {
		return todoDAO.getAll();
	}

	public Collection<TodoEntity> getAllTodos(int limit) {
		return todoDAO.getAll(limit);
	}

	public Collection<TodoEntity> getRange(Date from, Date to) {
		return todoDAO.getRange(from,to);
	}

	public void create(String text) {
		TodoEntity todo = new TodoEntity(null, text, new Date());
		todoDAO.create(todo);
	}

	public void create(String text, Date date) {
		TodoEntity todo = new TodoEntity(null, text, date);
		todoDAO.create(todo);
	}

	public void edit(Long id, String text) {
		TodoEntity todo = new TodoEntity(id, text, new Date());
		todoDAO.update(todo);
	}

	public void delete(Long id) {
		todoDAO.delete(id);
	}
}
