package com.revature.data;

import java.util.List;

import com.revature.beans.Grade;

public interface GradeDAO {

	public Grade getGrade(Integer id);

	public List<Grade> getAll();
	
}
