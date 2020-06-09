package com.revature.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceOracle;

public class LoginDelegate implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(LoginDelegate.class);
	private EmployeeService es = new EmployeeServiceOracle();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
			checkLogin(req, resp);
			break;
		case "POST":
			// logging in
			Employee emp = (Employee) session.getAttribute("loggedEmployee");
			if (emp != null) {
				respondWithUser(resp, emp);
			} else {
				checkLogin(req, resp);
			}
			break;
		case "DELETE":
			// logging out
			session.invalidate();
			// disassociates an id with a session and response says to delete cookie
			log.trace("User logged out");
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			break;
		default:
			break;
		}
	}

	private void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.trace("Logging in!");
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		if (emp != null) {
			respondWithUser(resp, emp);
		} else {

			// Need to see if we are an employee. Then we need to see if we are a customer.
			// Then we need to store that information in the session object.
			String username = req.getParameter("user");
			String password = req.getParameter("pass");
			log.trace((username + " " + password));
			emp = es.getEmployee(username, password);

			if (emp != null) {
				log.trace("employee being added to session");
				session.setAttribute("loggedEmployee", emp);
			}
			if (emp == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No employee found with those credentials");
			} else {
				respondWithUser(resp, emp);
			}
		}
	}

	private void respondWithUser(HttpServletResponse resp, Employee emp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		String e = om.writeValueAsString(emp);
		resp.getWriter().write(e.toString());
	}
}
