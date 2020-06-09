package com.revature.services;

import java.util.List;
import java.util.Set;

import com.revature.beans.Employee;
//import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;

public interface ReimbursementFormService {
//	public void addReimbursementForm(Employee emp, ReimbursementForm ref);
//	public List<ReimbursementForm> getReimbursementFormsByEmployee(Employee emp);
//	public boolean/void updateReimbursmentForm(Employee emp, ReimbursementForm ref);
	
	public ReimbursementForm addReimbursementForm(ReimbursementForm ref);
	public ReimbursementForm getReimbursementForm(int id);
	public void updateReimbursementForm(ReimbursementForm ref);
	public List<ReimbursementForm> getSupervisorForms(Integer supervisorId);
	public List<ReimbursementForm> getDepartmentForms(Integer departmentId);
	public List<ReimbursementForm> getAllForms(Employee emp);
	public List<ReimbursementForm> getFormsByEmp(int employeeId);
	public void delete(int reimbursementFormid);
}
