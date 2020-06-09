package com.revature.delegates;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;
import com.revature.services.ReimbursementFormService;
import com.revature.services.ReimbursementFormServiceOracle;

public class ReimbursementFormDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(ReimbursementFormDelegate.class);
	private ReimbursementFormService rfs = new ReimbursementFormServiceOracle();
	private ObjectMapper om = new ObjectMapper();


	//need to distinguish between operations being done on just one form
	//from operations being done on all the forms
	//and we need to figure out what that operation is

	//process method will determine if we are working with an operation that....
	//....works with several forms or a single form

	//single operations is for one form
	//collection operations is for multiple forms

	//once we figure out if the operation handles one form or multiple forms
	//then how do we operate GET,PUT,POST on one form or multiple forms

	//the switch-case will handle PUT, GET, POST differently

	//there will be a GET in the collection operations that returns a list of all the forms
	//based on who is asking (handled in services)
	//GET in the single operations will retrieve a form by ID and return info

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by ReimbursementForm delegate");
		HttpSession session = req.getSession();

		
		///////////////
		// come back to this
		///////////////
		
		
		String path = (String) req.getAttribute("path");
		log.trace("path: "+path);
		if(path==null||"".equals(path)) {
			// then we know we're operating on the entire collection. Only get (retrieval)
			// and post (creation) are allowed
			collectionOperations(req, resp);
		} else {
			// Either we're operating on a specific purchase or we're wrong
			// first, lets do what we would do if we're operating on a single purchase
//			if(path.indexOf("/")!=-1)
//				path = path.substring(0, path.indexOf("/"));
//			if (path.startsWith("actionable")) {
//				if("GET".equals(req.getMethod())) {
//					Set<Form> forms = fs.getActionableForms(emp);
//					if (forms.size() > 0) {
//						// Return JSON of forms
//						String data = om.writeValueAsString(forms);
//						log.trace(data);
//						resp.setContentType("application/json");
//						resp.getWriter().write(data);
//						resp.setStatus(HttpServletResponse.SC_OK);
//					} else {
//						resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//					}
//				} else {
//					resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
//				}
//			} else {
//				try {
//					singleOperations(req, resp, Integer.parseInt(path));
//				} catch(NumberFormatException e) {
//					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//				}
//			}
		}
	}



	private void singleOperations(HttpServletRequest req, HttpServletResponse resp, int parseInt) {
		// TODO Auto-generated method stub
		switch (req.getMethod()) {
		case "GET":
			break;

		default:
			break;
		}
	}
	private void collectionOperations(HttpServletRequest req, HttpServletResponse resp) {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		switch (req.getMethod()) {
		case "GET":
			///////
			//come back to this
			//////////
			List<ReimbursementForm> rfList = rfs.getFormsByEmp(emp.getId());
			try {
				resp.getWriter().write(om.writeValueAsString(rfList));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		case "POST":
			try {
				ReimbursementForm rf = new ReimbursementForm();
				//because when the user logs in when should have their id info
				rf.setEmployee(emp.getId());
				//timestamp adds a capital T for some reason
				//also apparently it doesn't include seconds
				//java.lang.IllegalArgumentException: Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
				rf.setDateAndTime(Timestamp.valueOf(req.getParameter("eventTime").replace("T", " ") + ":00"));
				rf.setLocationPlace(req.getParameter("locationplace"));
				rf.setDescriptionAnecdote(req.getParameter("descriptionAnecdote"));
				rf.setCostOfTraining(Integer.parseInt(req.getParameter("costOfTraining")));
				rf.setGradingFormat(Integer.parseInt(req.getParameter("gradeType")));
				rf.setEventType(Integer.parseInt(req.getParameter("eventType")));
				rf.setPassingGrade(req.getParameter("passingGrade"));

				/////////////////
				//	don't forget to fix this!
				////////////////

				rf.setApproval(1);
				//urgency.....
				//urgency is a boolean and not an Int or a String
				//so use an if statement here instead to set urgency
//				if (req.getParameter("urgency").equals("true")) {
//					rf.setUrgency(true);
//				} else {
				rf.setUrgency(false);
//				}
				rfs.addReimbursementForm(rf);
				resp.sendRedirect("static/ViewForms.html");
			}
			catch (Exception e) {
				e.printStackTrace();
			} 
			break;
		}
	}
}
