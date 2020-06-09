package com.revature.beans;

import java.sql.Timestamp;
//use Timestamp because it is easier
//much easier
//import java.time.LocalDateTime;

public class ReimbursementForm {
	private Integer id;
	private Integer employee;
	
	//including the datAndTime variable
	//import LocalDateTime <-----not anymore
	//import Timestamp
	private Timestamp dateAndTime;
	
	private String locationPlace;
	private String descriptionAnecdote;
	private Integer costOfTraining;
	private Integer gradingFormat;
	private Integer eventType;
	private String passingGrade;
	private Integer approval;

	
	//urgency will also be a boolean
	//check it in the oracle
	private Boolean urgency;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployee() {
		return employee;
	}

	public void setEmployee(Integer employee) {
		this.employee = employee;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getLocationPlace() {
		return locationPlace;
	}

	public void setLocationPlace(String locationPlace) {
		this.locationPlace = locationPlace;
	}

	public String getDescriptionAnecdote() {
		return descriptionAnecdote;
	}

	public void setDescriptionAnecdote(String descriptionAnecdote) {
		this.descriptionAnecdote = descriptionAnecdote;
	}

	public Integer getCostOfTraining() {
		return costOfTraining;
	}

	public void setCostOfTraining(Integer costOfTraining) {
		this.costOfTraining = costOfTraining;
	}

	public Integer getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(Integer gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public String getPassingGrade() {
		return passingGrade;
	}

	public void setPassingGrade(String passingGrade) {
		this.passingGrade = passingGrade;
	}

	public Integer getApproval() {
		return approval;
	}

	public void setApproval(Integer approval) {
		this.approval = approval;
	}

	public Boolean getUrgency() {
		return urgency;
	}

	public void setUrgency(Boolean urgency) {
		this.urgency = urgency;
	}

	public ReimbursementForm(Integer id, Integer employee, Timestamp dateAndTime, String locationPlace,
			String descriptionAnecdote, Integer costOfTraining, Integer gradingFormat, Integer eventType,
			String passingGrade, Integer approval, Boolean urgency) {
		super();
		this.id = id;
		this.employee = employee;
		this.dateAndTime = dateAndTime;
		this.locationPlace = locationPlace;
		this.descriptionAnecdote = descriptionAnecdote;
		this.costOfTraining = costOfTraining;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.passingGrade = passingGrade;
		this.approval = approval;
		this.urgency = urgency;
	}

	public ReimbursementForm() {
		super();
	}
	
	
}
