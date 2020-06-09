package com.revature.delegates;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Grade;
import com.revature.services.GradeTypeService;
import com.revature.services.GradeTypeServiceOracle;

public class GradeTypeDelegate implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(GradeTypeDelegate.class);
	private GradeTypeService serv = new GradeTypeServiceOracle();
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
				log.trace(req.getMethod() + " received by gradeType delegate");
				
				//do I need this???
				//HttpSession session = req.getSession();
				
				switch (req.getMethod()) {
				case "GET":
					List<Grade> typeList = serv.getAll();
					resp.getWriter().write(om.writeValueAsString(typeList));
					return;
				}
	}
}
