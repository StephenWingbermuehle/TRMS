package com.revature.delegates;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.EventType;
import com.revature.beans.ReimbursementForm;
import com.revature.beans.Status;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceOracle;
import com.revature.services.EventTypeService;
import com.revature.services.EventTypeServiceOracle;
import com.revature.services.ReimbursementFormService;
import com.revature.services.ReimbursementFormServiceOracle;
import com.revature.services.RequestForCommentService;
import com.revature.services.RequestForCommentServiceOracle;
import com.revature.services.StatusService;
import com.revature.services.StatusServiceOracle;

public class ApprovalDelegate implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(ApprovalDelegate.class);
	private StatusService serv = new StatusServiceOracle();
	private ReimbursementFormService rfserv = new ReimbursementFormServiceOracle();
	private EmployeeService empserv = new EmployeeServiceOracle();
	private EventTypeService etserv = new EventTypeServiceOracle();
	ObjectMapper om = new ObjectMapper();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//GET --- multiple
		// figure out if the Employee wants to see all the forms they want to approve
		//or see if they want a single/particular form
		//need to figure out who the use is (with emp id?)
		//is the user an employee? supervisor? department head? benCo?
		//if it is a supervisor, we'll have to get all the forms marked "supervisor approval pending" and belong to their subordinates?
		//if it is a department head, it will be all the the forms marked "department head pending" and belong to that department
		//if it is benCo, it will be all forms marked "benCo pending"
		
		
		//GET --- single
		//just return the form with the id
		
		//PUT --- single
		//update the status based on the employee submitting (can be a supervisor or department head submitting
		//if employee of rank PUTS form, then it should skip irrelevant approvals
	
		//this is a ridiculous amount of logic to be in the delegate
		//so I guess we're making another freaking service!
				log.trace(req.getMethod() + " received by Approval delegate");
				
				//do I need this???
				//apparently I will
				HttpSession session = req.getSession();
				
				//thank you, bookstore
				String path = (String) req.getAttribute("path");
				log.trace("path: "+path);
				
				Employee emp = (Employee) session.getAttribute("loggedEmployee");
				int reimbursementFormid = Integer.parseInt(path);
				
				switch (req.getMethod()) {
				
				case "DELETE":
					//come back for the delete method
					rfserv.delete(reimbursementFormid);
					
					return;
				//change approval from a GET request to a PUT request
				case "PUT":
					//List<EventType> typeList = serv.getAll();
					//resp.getWriter().write(om.writeValueAsString(typeList));
					serv.updateApproval(reimbursementFormid, emp);
					
					//make methods for calculating how much money the employee gets back
					//need the form, the employee, and the eventtype
					//form has that information
					
					ReimbursementForm rf = rfserv.getReimbursementForm(reimbursementFormid);
					
					Status formstat = serv.getStatus(rf.getApproval());
					
					//let the method check if the status is fully approved
					//we should only change the available reimbursement if the status is fully approved
					if( formstat.getCurrentState().equals("Fully Approved")) {
						Employee formEmp = empserv.getEmployeeById(rf.getEmployee());
						EventType formET = etserv.getEventType(rf.getEventType());
					
						//now we have the form, the event type, and the employee
						//hooray
					
						double availR = formEmp.getAvailableReimbursement(); 
						int cost = rf.getCostOfTraining();
						double coverage = formET.getPercentCoverage();
					
						//now we have the available reimbursement for the employee
							//we set availR as a double so that it can become a decimal when we multiple by the percentcoverage
						//we have the cost of the training that was submitted in the form
						//we now have the percentcoverage based on the event type
					
						availR = availR - cost*coverage;
						formEmp.setAvailableReimbursement(availR);
						
						empserv.updateEmployee(formEmp);
					}
					return;
				}
	}
	

}
