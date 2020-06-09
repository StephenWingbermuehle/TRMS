package com.revature.services;

import java.util.List;

import com.revature.beans.Grade;
import com.revature.data.GradeDAO;
import com.revature.data.GradeOracle;

public class GradeTypeServiceOracle implements GradeTypeService {
	GradeDAO gradeDAO = new GradeOracle();
	@Override
	public Grade getGrade(int id) {
		// TODO Auto-generated method stub
		return gradeDAO.getGrade(id);
	}

	@Override
	public List<Grade> getAll() {
		// TODO Auto-generated method stub
		return gradeDAO.getAll();
	}

	//just like the cardealership, we need a DAO object to...
		//...be used to interact with the Oracle methods
	
}
