package servlet;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import entity.TodoEntity;
import guice.GuiceModule;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.TodoService;

public class TodoServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		Injector injector = Guice.createInjector(new GuiceModule());
		TodoService todoService = injector.getInstance(TodoService.class);

		String action = request.getParameter("action");
		if ("new".equalsIgnoreCase(action)) {
			String text = request.getParameter("text");
			if (text != null && !text.equals("")) {
				todoService.create(text);
			}
		} else if ("edit".equalsIgnoreCase(action)) {
			Key key = KeyFactory.stringToKey(request.getParameter("id"));
			if (key != null) {
				String text = request.getParameter("text");
				if (text != null && !text.equals("")) {
					todoService.edit(key, text);
				}
			}
		} else if ("delete".equalsIgnoreCase(action)) {
			Key key = KeyFactory.stringToKey(request.getParameter("id"));
			if (key != null) {
				todoService.delete(key);
			}
		} else if ("benchmark-create".equalsIgnoreCase(action)) {
			long loops = 100;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < loops; i++) {
				todoService.create("Text "+ i);
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x insert takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-edit".equalsIgnoreCase(action)) {
			Collection<TodoEntity> todos = todoService.getAllTodos();
			long loops = 100;
			long startTime = System.currentTimeMillis();
			int i = 0;
			for (TodoEntity e : todos) {
				todoService.edit(e.getKey(), "Text "+ i);
				i++;
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x edit takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-delete".equalsIgnoreCase(action)) {
			Collection<TodoEntity> todos = todoService.getAllTodos();
			long loops = 100;
			long startTime = System.currentTimeMillis();
			for (TodoEntity e : todos) {
				todoService.delete(e.getKey());
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x delete takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-select".equalsIgnoreCase(action)) {
			int entities = 1000;
			long startTime = System.currentTimeMillis();
			todoService.getAllTodos(entities);
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println("select "+ entities +" entities takes "+ (endTime-startTime) +" millis");
			return;
		}

		Collection<TodoEntity> todos = todoService.getAllTodos();
		request.setAttribute("todos", todos);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/WEB-INF/viewTodos.jsp");
		rd.include(request, response);
    } 

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}
}
