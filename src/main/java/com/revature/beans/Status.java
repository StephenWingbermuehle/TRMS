package com.revature.beans;

public class Status {
	private Integer id;
	private String currentState;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
	
	public Status(Integer id, String currentState) {
		super();
		this.id = id;
		this.currentState = currentState;
	}
	
	
	public Status() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "Status [id=" + id + ", currentState=" + currentState + "]";
	}
	
	
}
