package com.revature.services;

import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;
import com.revature.beans.Status;
import com.revature.data.ReimbursementFormDAO;
import com.revature.data.ReimbursementFormOracle;
import com.revature.data.StatusDAO;
import com.revature.data.StatusOracle;

public class StatusServiceOracle implements StatusService {
//probably could have declared the DAOs up here buuuuuut that's a highlight and a right-click i don't want to do
	
	@Override
	public void updateApproval(int id, Employee emp) {
		// TODO Auto-generated method stub
		
		//need to call the reimbursmenrtformDAO to update the form
		//call the statusDAO to get the status we need to set
		ReimbursementFormDAO rfd = new ReimbursementFormOracle();
		StatusDAO sd = new StatusOracle();
		ReimbursementForm rf = rfd.getReimbursementForm(id);
		
		switch (emp.getTitle()) {
		//what are the titles going to be?
			// jedi apprentice, jedi knight, jedi master, benCo
		//what are the statuses going to be?
			//knight pending, master pending, benCo pending, fully approved
		case "jedi knight":
			//rather than make another freaking method in my DAO...
			//nope
			//call the Status table to find the id that matches that status
			//set the approval id to that status on the reimbursement form
			rf.setApproval(sd.getStatus("Master Pending").getId());
			rfd.updateReimbursementForm(rf);
			break;
		case "jedi master":
			rf.setApproval(sd.getStatus("BenCo Pending").getId());
			rfd.updateReimbursementForm(rf);
			break;
		case "benCo":
			rf.setApproval(sd.getStatus("Fully Approved").getId());
			rfd.updateReimbursementForm(rf);
			break;
		default:
			rf.setApproval(sd.getStatus("Knight Pending").getId());
			rfd.updateReimbursementForm(rf);
		}
	}

	@Override
	public Status getStatus(Integer id) {
		// TODO Auto-generated method stub
		StatusDAO sd = new StatusOracle();
		return sd.getStatus(id) ;
	}
	
}
