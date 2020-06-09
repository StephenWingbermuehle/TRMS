package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class EmployeeOracle implements EmployeeDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(EmployeeOracle.class);
		

	public Employee getEmployee(String username, String pass) {
		log.trace("Attempting to find Employee with username = " + username + ", pass = " + pass);
		Employee emp = new Employee();
		try (Connection conn = cu.getConnection()) {
			String sql = "select id, firstName, lastName, username, pass, title, supervisor, departmentId, availableReimbursement from Employee where username = ? and pass = ?";
			//sql = "SELECT * FROM Employee";
			//System.out.println(conn+"1");
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found Employee with username: " + rs.getString("username") + " and pass: " + rs.getString("pass"));
				emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setUsername(rs.getString("username"));
				emp.setPass(rs.getString("pass"));
				emp.setTitle(rs.getString("title"));
				emp.setFirstName(rs.getString("firstName"));
				emp.setLastName(rs.getString("lastName"));
				emp.setSupervisor(rs.getInt("supervisor"));
				emp.setDepartmentId(rs.getInt("departmentId"));
				emp.setAvailableReimbursement(rs.getDouble("availableReimbursement"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		log.trace("getEmployee returning " + emp);
		return emp;
	}


	@Override
	public Set<Employee> getEmployees() {
		Set<Employee> empList = new HashSet<Employee>();
		try(Connection conn = cu.getConnection())
		{
			String sql = "select id, firstName, lastName, username, pass, title, supervisor, departmentId, availableReimbursement from Employee";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				Employee emp = new Employee();
				if(rs.getInt("supervisor")==0)
				{
					log.trace("null supervisor");
				} 
				emp.setId(rs.getInt("id"));
				emp.setUsername(rs.getString("username"));
				emp.setPass(rs.getString("pass"));
				emp.setTitle(rs.getString("title"));
				emp.setFirstName(rs.getString("firstName"));
				emp.setLastName(rs.getString("lastName"));
				emp.setSupervisor(rs.getInt("supervisor"));
				emp.setDepartmentId(rs.getInt("departmentId"));
				emp.setAvailableReimbursement(rs.getDouble("availableReimbursemnt"));
				empList.add(emp);
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e,EmployeeOracle.class);
		}
		return empList;
	}


	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
		Employee emp = null;
		try (Connection conn = cu.getConnection()) {
			log.trace("retrieving employee fields");
			String sql = "select id, username, pass, firstName, lastName, supervisor, title, departmentId, availableReimbursement from Employee where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				log.trace("employee has been found");
				emp = new Employee();
				emp.setId(id);
				emp.setFirstName(rs.getString("firstName"));
				emp.setLastName(rs.getString("lastName"));
				emp.setUsername(rs.getString("username"));
				emp.setPass(rs.getString("pass"));
				emp.setTitle(rs.getString("title"));
				//supervisor is an int, not a string
				//and we wouldn't use id here because that's a different column
				emp.setSupervisor(rs.getInt("supervisor"));
				emp.setDepartmentId(rs.getInt("departmentId"));
				emp.setAvailableReimbursement(rs.getDouble("availableReimbursement"));
			}
		} catch (Exception e) {
			LogUtil.logException(e, EmployeeOracle.class);
		}
		return emp;
	}


	@Override
	public Employee updateEmployee(Employee employee) {
		log.trace("Updating Employee to " + employee);
		Connection conn = null;
		try {
			conn = cu.getConnection();
			// JDBC Automatically commits all data unless
			// you tell it not to.
			conn.setAutoCommit(false);
			String sql = "update Employee set firstName = ?, lastName = ?, username = ?, pass = ?, " +
						"title = ?, supervisor = ?, departmentId = ?, availableReimbursement = ? " +
						"where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			//make sure you count how many fields you have (not 12, it's 11)
			//and also maybe don't mispell things in your SQL statement!!!!!
			//things like descriptionAnecdote!!!!!
			//lesson = use easier names next time
			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getLastName());
			stmt.setString(3, employee.getUsername());
			stmt.setString(4, employee.getPass());
			stmt.setString(5, employee.getTitle());
			stmt.setInt(6, employee.getSupervisor());
			stmt.setInt(7, employee.getDepartmentId());
			stmt.setDouble(8, employee.getAvailableReimbursement());
			stmt.setInt(9, employee.getId());
			int rs = stmt.executeUpdate();
			if (rs != 1) {
				log.warn("Employee update failed.");
				conn.rollback();
				return employee;
			}
			log.info("Employee updated!");
			conn.commit();
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, EmployeeOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, EmployeeOracle.class);
			}
		} 
		return null;
	} 

}
