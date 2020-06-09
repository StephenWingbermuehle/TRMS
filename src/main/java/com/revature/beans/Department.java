package com.revature.beans;

public class Department {
	private Integer id;
	private String departName;
	private Integer departHead;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Integer getDepartHead() {
		return departHead;
	}
	public void setDepartHead(Integer departHead) {
		this.departHead = departHead;
	}
	

	public Department(Integer id, String departName, Integer departHead) {
		super();
		this.id = id;
		this.departName = departName;
		this.departHead = departHead;
	}
	
	
	public Department() {
		super();
	}
	
	
}
