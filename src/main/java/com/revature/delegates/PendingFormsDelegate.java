package com.revature.delegates;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;
import com.revature.services.ReimbursementFormService;
import com.revature.services.ReimbursementFormServiceOracle;

public class PendingFormsDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(PendingFormsDelegate.class);
	private ReimbursementFormService serv = new ReimbursementFormServiceOracle();
	private ObjectMapper om = new ObjectMapper();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
				log.trace(req.getMethod() + " received by PendingForms delegate");
				HttpSession session = req.getSession();
				Employee emp = (Employee) session.getAttribute("loggedEmployee");
				//do I need this???
				//HttpSession session = req.getSession();
				
				switch (req.getMethod()) {
				case "GET":
					List<ReimbursementForm> rfList = serv.getAllForms(emp);
					resp.getWriter().write(om.writeValueAsString(rfList));
					return;
				}
	}

}
