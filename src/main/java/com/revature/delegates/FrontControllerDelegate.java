package com.revature.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FrontControllerDelegate {
	void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException;
}
// every other delegate is going to implement the front controller
// front controller is our entry point
//this is pulled directly from the Bookstroe's FrontController'