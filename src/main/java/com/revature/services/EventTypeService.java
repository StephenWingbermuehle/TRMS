package com.revature.services;

import java.util.List;

import com.revature.beans.EventType;

public interface EventTypeService {
	public EventType getEventType(int id);
	public List<EventType> getAll();
}
