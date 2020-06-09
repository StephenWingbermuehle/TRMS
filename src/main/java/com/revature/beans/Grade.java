package com.revature.beans;

public class Grade {
	private Integer id;
	private String gradeType;
	
	//this is going to be like a Boolean
	//we will check this in the Oracle
	private Boolean requirePresentation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGradeType() {
		return gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}

	public Boolean getRequirePresentation() {
		return requirePresentation;
	}

	public void setRequirePresentation(Boolean requirePresentation) {
		this.requirePresentation = requirePresentation;
	}

	public Grade(Integer id, String gradeType, Boolean requirePresentation) {
		super();
		this.id = id;
		this.gradeType = gradeType;
		this.requirePresentation = requirePresentation;
	}

	public Grade() {
		super();
	}
	
	
	
}
