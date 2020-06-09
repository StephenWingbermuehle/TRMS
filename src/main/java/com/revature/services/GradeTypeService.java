package com.revature.services;

import java.util.List;

import com.revature.beans.Grade;

public interface GradeTypeService {
	public Grade getGrade(int id);
	public List<Grade> getAll();
}
