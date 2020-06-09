package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.beans.Department;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class DepartmentOracle implements DepartmentDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(DepartmentOracle.class);
	
	
	public Department getDepartment(Integer id) {
		log.trace("Attempting to find department with id = " + id);
		Department d = null;
		String sql = "select id, DepartHead, DepartName from department where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found department with id:  " + rs.getInt("id"));
				d = new Department();
				d.setId(rs.getInt("id"));
				d.setDepartHead(rs.getInt("DepartHead"));
				d.setDepartName(rs.getString("DepartName"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, DepartmentOracle.class);
		}
		log.trace("getDepartment returning " + d);
		return d;
	}

}
