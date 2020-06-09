package com.revature.beans;

public class EventType {
	private Integer id;
	private String eventType;
	private Double percentCoverage;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Double getPercentCoverage() {
		return percentCoverage;
	}
	public void setPercentCoverage(Double percentCoverage) {
		this.percentCoverage = percentCoverage;
	}
	
	
	public EventType(Integer id, String eventType, Double percentCoverage) {
		super();
		this.id = id;
		this.eventType = eventType;
		this.percentCoverage = percentCoverage;
	}
	
	
	public EventType() {
		super();
	}
	
	
}
