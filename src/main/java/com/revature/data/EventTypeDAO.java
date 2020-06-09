package com.revature.data;

import java.util.List;

import com.revature.beans.EventType;

public interface EventTypeDAO {

	public EventType getEventType(Integer id);

	//this is needed for the EventTypeService
	//the idea is at some point, we actually will need to pull all the EventTypes
	//now I have to go aaaaaallll the way back to the Bean and create this method
	public List<EventType> getAll();
	//TODO make the method in Bean
}
