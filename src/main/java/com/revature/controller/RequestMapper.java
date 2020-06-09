package com.revature.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.delegates.ApprovalDelegate;
import com.revature.delegates.FrontControllerDelegate;
import com.revature.delegates.GradeTypeDelegate;
import com.revature.delegates.LoginDelegate;
import com.revature.delegates.PendingFormsDelegate;
import com.revature.delegates.ReimbursementFormDelegate;
import com.revature.delegates.RequestForCommentDelegate;
import com.revature.delegates.TypesDelegate;

public class RequestMapper {
	private Logger log = Logger.getLogger(RequestDispatcher.class);

	private Map<String, FrontControllerDelegate> delegateMap;

	public FrontControllerDelegate dispatch(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		//////////////////////////////////////////////////////
		// ADD CORS - thank me later
		addCorsHeader(req.getRequestURI(), resp);
		if ("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {
			}; // empty delegate
		}
		/////////////////////////////////////////////////////

		log.trace("Calling our Request Delegate: " + req.getRequestURI());
		log.trace(req.getRequestURI().substring(req.getContextPath().length() + 1));
		String uriWithoutContext = req.getRequestURI();
		uriWithoutContext = uriWithoutContext.substring(req.getContextPath().length() + 1);
		String uriJustType = uriWithoutContext;
		if(uriWithoutContext.indexOf("/") != -1) {
			uriJustType = uriWithoutContext.substring(0, uriWithoutContext.indexOf("/"));
			// I'd like to save everything after the type
			// /genres/1 -> save /1
			String afterType = uriWithoutContext.substring(uriWithoutContext.indexOf("/")+1);
			if(!"".equals(afterType)) {
				req.setAttribute("path", afterType);
				log.trace("Path is equal to: "+afterType);
			}
		}
		log.trace(uriJustType);		

		return delegateMap.get(uriJustType);
	}
// need to change these below...
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		delegateMap.put("ReimbursementForm", new ReimbursementFormDelegate());
		delegateMap.put("approval", new ApprovalDelegate());
		delegateMap.put("login", new LoginDelegate());
		delegateMap.put("request", new RequestForCommentDelegate());
		delegateMap.put("eventTypes", new TypesDelegate());
		delegateMap.put("PendingForms", new PendingFormsDelegate());
		delegateMap.put("gradeType",  new GradeTypeDelegate());
		delegateMap.put("", (req, resp) -> {
			resp.sendRedirect("http://localhost:8080/TRMS/static/login.html");
//			req.getRequestDispatcher("/static/login.html")
//				.forward(req, resp);
		});
	}

	// futureproofing
	private void addCorsHeader(String requestURI, HttpServletResponse resp) {
		log.trace("adding headers");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		resp.addHeader("Vary", "Origin");
		// if I don't care about getting my cookie, this will work
		// response.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Max-Age", "1728000");
		resp.addHeader("Produces", "application/json");
	}

}
