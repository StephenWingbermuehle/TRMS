package com.revature.delegates;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.EventType;
import com.revature.services.EventTypeService;
import com.revature.services.EventTypeServiceOracle;

public class TypesDelegate implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(TypesDelegate.class);
	private EventTypeService serv = new EventTypeServiceOracle();
	private ObjectMapper om = new ObjectMapper();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by eventtype delegate");
		
		//do I need this???
		//HttpSession session = req.getSession();
		
		switch (req.getMethod()) {
		case "GET":
			List<EventType> typeList = serv.getAll();
			resp.getWriter().write(om.writeValueAsString(typeList));
			return;
		}
	}

}
