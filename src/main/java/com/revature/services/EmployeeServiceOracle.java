package com.revature.services;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.data.EmployeeDAO;
import com.revature.data.EmployeeOracle;
//import com.revature.utils.LogUtil;

public class EmployeeServiceOracle implements EmployeeService {
	private Logger log = Logger.getLogger(EmployeeServiceOracle.class);
	private EmployeeDAO ed = new EmployeeOracle();
	
	@Override
	public Employee getEmployee(String username, String pass) {
		Employee emp = new Employee();
		emp = ed.getEmployee(username, pass);
		if(emp.getId()==null)
		{
			log.trace("No employee found");
			return null;
		}
		return emp;
	}

	@Override
	public Employee getEmployeeById(int i) {
		log.trace("retrieving employee by id: "+i);
		Employee emp = new Employee(i);
		//don't have a user bean sooooo this is useless
		//goodbye getUser()
//		emp = (Employee) ud.getUser(emp);
		emp = ed.getEmployeeById(i);
		if(emp.getId()==0)
		{
			log.trace("No employee found");
			return null;
		}
		//this if method isn't necessary because my Employee bean only takes in the supervisor as an id
		//this would be if the supervisor is an object
//		if(emp.getSupervisor()!=null)
//		{
//			log.trace("Retrieving supervisor");
//			emp.setSupervisor(getEmployeeById(emp.getSupervisor().getId()));
//		}
		return emp;
	}

	@Override
	public Set<Employee> getEmployees() {
		Set<Employee> empList = ed.getEmployees();
		//I don't know if I need this for loop because I'm already returning emList in my Oracle in data package
//		for(Employee e : empList)
//		{
//			emp.getEmployeeById(e);
//			e.setSupervisor(getEmployeeById(e.getSupervisor().getId()));
//		}
		return empList;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return ed.updateEmployee(employee);
	}
}
