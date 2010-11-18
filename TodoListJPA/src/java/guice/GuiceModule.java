package guice;

import com.google.inject.AbstractModule;
import dao.TodoDAO;
import dao.TodoDAOImpl;

public class GuiceModule extends AbstractModule {
	public void configure() {
		bind(TodoDAO.class).to(TodoDAOImpl.class);
	}
}
