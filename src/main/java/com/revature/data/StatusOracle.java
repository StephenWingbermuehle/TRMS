package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.beans.Status;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class StatusOracle implements StatusDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(StatusOracle.class);
	
	public Status getStatus(Integer id) {
		log.trace("Attempting to find status with id = " + id);
		Status s = null;
		String sql = "select id, currentState from Status where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found status with id:  " + rs.getInt("id"));
				s = new Status();
				s.setId(rs.getInt("id"));
				s.setCurrentState(rs.getString("currentState"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, DepartmentOracle.class);
		}
		log.trace("getStatus returning " + s);
		return s;
	}

	public void updateStatus(Status status) {
		log.trace("Updating Status to " + status);
		Connection conn = null;
		try {
			conn = cu.getConnection();
			// JDBC Automatically commits all data unless
			// you tell it not to.
			conn.setAutoCommit(false);
			String sql = "update Status set status = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(2, status.getId());
			stmt.setString(1, status.getCurrentState());
			int rs = stmt.executeUpdate();
			if (rs != 1) {
				log.warn("Status update failed.");
				conn.rollback();
				return;
			}
			log.info("Status updated!");
			conn.commit();
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, StatusOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, StatusOracle.class);
			}
		}
	}

	//making this method because looking for jedi knights and the approval status
	//associated with them is hard
	@Override
	public Status getStatus(String currentState) {
		// TODO Auto-generated method stub
		log.trace("Attempting to find status with currentState = " + currentState);
		Status s = null;
		String sql = "select id, currentState from Status where currentState = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, currentState);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found status with currentState:  " + rs.getInt("id"));
				s = new Status();
				s.setId(rs.getInt("id"));
				s.setCurrentState(rs.getString("currentState"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, DepartmentOracle.class);
		}
		log.trace("getStatus returning " + s);
		return s;
	}

}
