package com.revature.beans;

//each table from SQL database needs a bean class in beans package
public class Employee {
	
	
	//each column from SQL table will have a field Object variable
	private Integer id;
	private String firstName;
	private String lastName;
	private String username;
	private String pass;
	private String title;
	private Integer supervisor;
	private Integer departmentId;
	private Double availableReimbursement;
	
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Double getAvailableReimbursement() {
		return availableReimbursement;
	}
	public void setAvailableReimbursement(Double availableReimbursement) {
		this.availableReimbursement = availableReimbursement;
	}
	//each variable needs a getter and setter
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Integer getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Integer supervisor) {
		this.supervisor = supervisor;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	//generate a constructor for the class with all the field variables
	public Employee(Integer id, String firstName, String lastName, String username, String pass, String title, Integer supervisor, Integer departmentId, Double availableReimbursement) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.pass = pass;
		this.title = title;
		this.supervisor = supervisor;
		this.departmentId = departmentId;
		this.availableReimbursement = availableReimbursement;
	}
	
	//turns out we'll probably need this in the services
	public Employee(Integer id) {
		super();
		this.id = id;
	}
	//generate another constructor for just the class
	public Employee() {
		super();
	}
	
	//you can generate more constructors as needed
	//and insert them below
	
	
}
