package com.revature.data;

import com.revature.beans.Status;

public interface StatusDAO {

	public Status getStatus(Integer id);
	
	public void updateStatus(Status status);
	
	public Status getStatus(String currentState);
}
