package com.revature.services;

import java.util.Set;

import com.revature.beans.Employee;

public interface EmployeeService {

		public Employee getEmployee(String username, String pass);
		public Employee getEmployeeById(int i);
		public Set<Employee> getEmployees();
		public Employee updateEmployee(Employee employee);
}
