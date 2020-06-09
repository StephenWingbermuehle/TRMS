package com.revature.data;

import java.util.List;
import java.util.Set;

import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;

public interface ReimbursementFormDAO {

	public ReimbursementForm addReimbursementForm(ReimbursementForm reimbursementForm);
	
	public void updateReimbursementForm(ReimbursementForm reimbursementForm);
	
	public ReimbursementForm getReimbursementForm(Integer id);
	
	//go to the employee table and get the supervisors then get the forms associated with those supervisors
	public List<ReimbursementForm> getSupervisorForms(Integer supervisorId);
	
	//getting forms by looking at the department and the employees associated with that department and then the forms for those users
	public List<ReimbursementForm> getDepartmentForms(Integer departmentId);
	
	public List<ReimbursementForm> getAllForms();
	
	public List<ReimbursementForm> getFormsByEmp(int employeeId);

	public void delete(int reimbursementFormid);	
}
