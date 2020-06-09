package com.revature.data;

import java.util.Set;

import com.revature.beans.Employee;

public interface EmployeeDAO {
		
		public Employee getEmployee(String username, String pass);
		
		public Set<Employee> getEmployees();
		
		public Employee getEmployeeById(int id);
		
		public Employee updateEmployee(Employee employee);
}
