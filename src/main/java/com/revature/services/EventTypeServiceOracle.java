package com.revature.services;

import java.util.List;

import com.revature.beans.EventType;
import com.revature.data.EventTypeDAO;
import com.revature.data.EventTypeOracle;
//darn naming conventions!!!!

public class EventTypeServiceOracle implements EventTypeService{
	
	//just like the cardealership, we need a DAO object to...
	//...be used to interact with the Oracle methods
	EventTypeDAO eventTypeD = new EventTypeOracle();
	@Override
	public EventType getEventType(int id) {
		// TODO Auto-generated method stub
		return eventTypeD.getEventType(id);
	}

	@Override
	public List<EventType> getAll() {
		// TODO Auto-generated method stub
		return eventTypeD.getAll();
	}

}
