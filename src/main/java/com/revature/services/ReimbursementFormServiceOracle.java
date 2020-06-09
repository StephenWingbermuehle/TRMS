package com.revature.services;

import java.util.List;
import java.util.Set;

import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;
import com.revature.data.ReimbursementFormDAO;
import com.revature.data.ReimbursementFormOracle;
//import com.revature.data.StatusDAO;
//import com.revature.data.StatusOracle;

public class ReimbursementFormServiceOracle implements ReimbursementFormService{
	//make the DAO object like always
	ReimbursementFormDAO rfd = new ReimbursementFormOracle();
	
	@Override
	public ReimbursementForm addReimbursementForm(ReimbursementForm ref) {
		// TODO Auto-generated method stub
		return rfd.addReimbursementForm(ref);
	}

	@Override
	public ReimbursementForm getReimbursementForm(int id) {
		// TODO Auto-generated method stub
		return rfd.getReimbursementForm(id);
	}

	@Override
	public void updateReimbursementForm(ReimbursementForm ref) {
		// TODO Auto-generated method stub
		//technically, our update method doesn't have a return statement
		rfd.updateReimbursementForm(ref);
	}

	@Override
	public List<ReimbursementForm> getSupervisorForms(Integer supervisorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReimbursementForm> getDepartmentForms(Integer departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReimbursementForm> getFormsByEmp(int employeeId) {
		// TODO Auto-generated method stub
		return rfd.getFormsByEmp(employeeId);
	}

	@Override
	public List<ReimbursementForm> getAllForms(Employee emp) {
		// TODO Auto-generated method stub
		
				//need to call the reimbursmenrtformDAO to update the form
				//call the statusDAO to get the status we need to set
				ReimbursementFormDAO rfd = new ReimbursementFormOracle();
				
				switch (emp.getTitle()) {
				//what are the titles going to be?
					// jedi apprentice, jedi knight, jedi master, benCo
				//what are the statuses going to be?
					//knight pending, master pending, benCo pending, fully approved
				case "jedi knight":
					//if a supervisor (jedi knight) wants to view forms
					//they should get SupervisorForms by their own employee id
					//cake
					return rfd.getSupervisorForms(emp.getId());
				case "jedi master":
					//if a jedi master (department head) wants to get forms
					//they should be able to get all the forms from employees and supervisors
					//that are from that department (by searching the department id)
					return rfd.getDepartmentForms(emp.getDepartmentId());
				case "benCo":
					//benCo comes in and wants to see the forms submitted
					//they should be allowed to see every single form
					return rfd.getAllForms();
				default:
					//an employee (or anyone without an important title i.e. a default title)
					//will not be able to see any forms for their approval
					//because they have no approval authority
					//so we return null here
					return null;
				}
			}

	@Override
	public void delete(int reimbursementFormid) {
		// TODO Auto-generated method stub
		rfd.delete(reimbursementFormid);
	}
}
