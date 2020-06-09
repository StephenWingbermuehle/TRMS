package com.revature.services;

import com.revature.beans.Employee;
import com.revature.beans.Status;

public interface StatusService {

	public void updateApproval(int id, Employee emp);
	
	public Status getStatus(Integer id);
}
