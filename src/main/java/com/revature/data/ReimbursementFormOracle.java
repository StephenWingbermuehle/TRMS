package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
//these imports are not needed because we changed data type
// from LocalDate to Timestamp
//import java.time.ZoneId;
//import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.ReimbursementForm;
//import com.revature.beans.RequestForComment;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class ReimbursementFormOracle implements ReimbursementFormDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(ReimbursementFormOracle.class);
	
	public ReimbursementForm addReimbursementForm(ReimbursementForm reimbursementForm) {
		int key = 0;
		log.trace("adding reimbursementForm to the database: "+ reimbursementForm);
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "insert into ReimbursementForm(employee, dateAndTime, locationPlace, descriptionAnecdote, "
					+ "costOfTraining, gradingFormat, eventType, passingGrade, approval, urgency) values (?,?,?,?,?,?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
//			pstm.setInt(1, reimbursementForm.getId());
			pstm.setInt(1, reimbursementForm.getEmployee());
			//thank you, Baeldung.com
			pstm.setTimestamp(2, reimbursementForm.getDateAndTime());
			pstm.setString(3, reimbursementForm.getLocationPlace());
			pstm.setString(4, reimbursementForm.getDescriptionAnecdote());		
			pstm.setInt(5, reimbursementForm.getCostOfTraining());
			pstm.setInt(6, reimbursementForm.getGradingFormat());
			pstm.setInt(7, reimbursementForm.getEventType());
			pstm.setString(8, reimbursementForm.getPassingGrade());
			pstm.setInt(9, reimbursementForm.getApproval());
			pstm.setBoolean(10, reimbursementForm.getUrgency());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			if(rs.next()) {
				log.trace("ReimbursementForm created");
				key = rs.getInt(1);
				reimbursementForm.setId(key);
				conn.commit();
			} else {
				log.trace("ReimbursementForm not created!");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, ReimbursementFormOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				LogUtil.logException(e1, ReimbursementForm.class);
			}
		}
		return getReimbursementForm(key);
	}

	public void updateReimbursementForm(ReimbursementForm reimbursementForm) {
		log.trace("Updating ReimbursementForm to " + reimbursementForm);
		Connection conn = null;
		try {
			conn = cu.getConnection();
			// JDBC Automatically commits all data unless
			// you tell it not to.
			conn.setAutoCommit(false);
			String sql = "update ReimbursementForm set employee = ?, dateAndTime = ?, locationPlace = ?, descriptionAnecdote = ?, " +
						"costOfTraining = ?, gradingFormat = ?, eventType = ?, passingGrade = ?, approval = ?, " +
						"urgency = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			//make sure you count how many fields you have (not 12, it's 11)
			//and also maybe don't mispell things in your SQL statement!!!!!
			//things like descriptionAnecdote!!!!!
			//lesson = use easier names next time
			stmt.setInt(11, reimbursementForm.getId());
			stmt.setInt(1, reimbursementForm.getEmployee());
			
			//from Baeldung.com
			//just copy their way of doing it
			stmt.setTimestamp(2, reimbursementForm.getDateAndTime());
			stmt.setString(3, reimbursementForm.getLocationPlace());
			stmt.setString(4, reimbursementForm.getDescriptionAnecdote());
			stmt.setInt(5, reimbursementForm.getCostOfTraining());
			stmt.setInt(6, reimbursementForm.getGradingFormat());
			stmt.setInt(7, reimbursementForm.getEventType());
			stmt.setString(8, reimbursementForm.getPassingGrade());
			stmt.setInt(9, reimbursementForm.getApproval());
			stmt.setBoolean(10, reimbursementForm.getUrgency());
			int rs = stmt.executeUpdate();
			if (rs != 1) {
				log.warn("ReimbursementForm update failed.");
				conn.rollback();
				return;
			}
			log.info("ReimbursementForm updated!");
			conn.commit();
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, ReimbursementFormOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, ReimbursementFormOracle.class);
			}
		}
	}

	public ReimbursementForm getReimbursementForm(Integer id) {
		log.trace("Attempting to find ReimbursementForm with id = " + id);
		ReimbursementForm r = null;
		String sql = "select id, employee, dateAndTime, locationPlace, descriptionAnecdote, costOfTraining, gradingFormat, " +
						"eventType, passingGrade, approval, urgency from ReimbursementForm where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found ReimbursementForm with id:  " + rs.getInt("id"));
				r = new ReimbursementForm();
				r.setId(rs.getInt("id"));
				r.setEmployee(rs.getInt("employee"));
				
				//this is from Baeldung.com "date to localdate and localdatetime"
				//copy paste from Baeldung.com
				//OK....so, when we change the dtate type in the bean....
				//... from LocalDate to Timestamp, then this gets significantly easier
				r.setDateAndTime(rs.getTimestamp("dateAndTime"));
				r.setLocationPlace(rs.getString("locationPlace"));
				r.setDescriptionAnecdote(rs.getString("descriptionAnecdote"));
				r.setCostOfTraining(rs.getInt("costOfTraining"));
				r.setGradingFormat(rs.getInt("gradingFormat"));
				r.setEventType(rs.getInt("eventType"));
				r.setPassingGrade(rs.getString("passingGrade"));
				r.setApproval(rs.getInt("approval"));
				r.setUrgency(rs.getBoolean("urgency"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, ReimbursementFormOracle.class);
		}
		log.trace("getReimbursementForm returning " + r);
		return r;
	}

	@Override
	public List<ReimbursementForm> getSupervisorForms(Integer supervisorId) {
		List<ReimbursementForm> rfl = new ArrayList<ReimbursementForm>();
		log.trace("Attempting to find ReimbursementForm with  supervisor id = " + supervisorId);
		//time to do a join in SQL
		//connect ReimbursementForm and Employee
		//get all forms where supervisor = ?
		String sql = "select * from ReimbursementForm JOIN Employee ON ReimbursementForm.employee = Employee.id where Employee.supervisor = ?";
		
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, supervisorId);
			ResultSet rs = stmt.executeQuery();
			log.trace(rs.next());
			while (rs.next()) {
				ReimbursementForm rf = new ReimbursementForm();
				rf.setId(rs.getInt("id"));
				rf.setEmployee(rs.getInt("employee"));
				rf.setDateAndTime(rs.getTimestamp("dateAndTime"));
				rf.setLocationPlace(rs.getString("locationPlace"));
				rf.setDescriptionAnecdote(rs.getString("descriptionAnecdote"));
				rf.setCostOfTraining(rs.getInt("costOfTraining"));
				rf.setGradingFormat(rs.getInt("gradingFormat"));
				rf.setEventType(rs.getInt("eventType"));
				rf.setPassingGrade(rs.getString("passingGrade"));
				rf.setApproval(rs.getInt("approval"));
				rf.setUrgency(rs.getBoolean("urgency"));
				rfl.add(rf);
			}
		} catch (Exception ex) {
			LogUtil.logException(ex, ReimbursementFormOracle.class);
		}
		return rfl;
	}

	//this should be exactly like the getSupervisorForms method
	//some minor changes
	@Override
	public List<ReimbursementForm> getDepartmentForms(Integer departmentId) {
		List<ReimbursementForm> rfl = new ArrayList<ReimbursementForm>();
		log.trace("Attempting to find ReimbursementForm with  department id = " + departmentId);
		//Stack Overflow suggests using select Distinct...
//		String sql = "select id, isbn10, isbn13, title, price, stock, cover"
//				+ " from book join book_author on book.id=book_author.book_id where author_id =?";
//		String sql = "select * FROM ReimbursementForm INNER JOIN Employee ON ReimbursementForm.employee = Employee.departmentId where departmentId = ?";
		//	is the same problem as...
//		String sql = "select * FROM ReimbursementForm LEFT OUTER JOIN Employee ON ReimbursementForm.employee = Employee.departmentId where departmentId = ?";
		/////////////////
		//need help
		/////////////////
		//String sql = "select * FROM ReimbursementForm LEFT OUTER JOIN Employee ON ReimbursementForm.employee = Employee.departmentId where departmentId = ?";
		
		String sql = "select rf.id as id, rf.employee as employee, rf.dateAndTime as dateAndTime, rf.locationPlace as locationPlace,"
				+ " rf.descriptionAnecdote as descriptionAnecdote, rf.costOfTraining as costOfTraining, rf.gradingFormat as gradingFormat,"
				+ " rf.eventType as eventType, rf.passingGrade as passingGrade, rf.approval as approval, rf.urgency as urgency"
				+ " from ReimbursementForm rf LEFT OUTER JOIN Employee re on rf.employee = re.id where re.departmentId = ? ORDER BY re.id";
		
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, departmentId);
			ResultSet rs = stmt.executeQuery();
			// log.trace(rs.next());
			while (rs.next()) {
				ReimbursementForm rf = new ReimbursementForm();
				rf.setId(rs.getInt("id"));
				rf.setEmployee(rs.getInt("employee"));
				rf.setDateAndTime(rs.getTimestamp("dateAndTime"));
				rf.setLocationPlace(rs.getString("locationPlace"));
				rf.setDescriptionAnecdote(rs.getString("descriptionAnecdote"));
				rf.setCostOfTraining(rs.getInt("costOfTraining"));
				rf.setGradingFormat(rs.getInt("gradingFormat"));
				rf.setEventType(rs.getInt("eventType"));
				rf.setPassingGrade(rs.getString("passingGrade"));
				rf.setApproval(rs.getInt("approval"));
				rf.setUrgency(rs.getBoolean("urgency"));
				rfl.add(rf);
			}
		} catch (Exception ex) {
			LogUtil.logException(ex, ReimbursementFormOracle.class);
		}
		for (ReimbursementForm form:rfl) {
			log.trace(form.getId());
		}
		return rfl;
	}

	@Override
	public List<ReimbursementForm> getAllForms() {
		List<ReimbursementForm> rfl = new ArrayList<ReimbursementForm>();
		log.trace("retrieving all forms");
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from ReimbursementForm";

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ReimbursementForm rf = new ReimbursementForm();
				rf.setId(rs.getInt("id"));
				rf.setEmployee(rs.getInt("employee"));
				rf.setDateAndTime(rs.getTimestamp("dateAndTime"));
				rf.setLocationPlace(rs.getString("locationPlace"));
				rf.setDescriptionAnecdote(rs.getString("descriptionAnecdote"));
				rf.setCostOfTraining(rs.getInt("costOfTraining"));
				rf.setGradingFormat(rs.getInt("gradingFormat"));
				rf.setEventType(rs.getInt("eventType"));
				rf.setPassingGrade(rs.getString("passingGrade"));
				rf.setApproval(rs.getInt("approval"));
				rf.setUrgency(rs.getBoolean("urgency"));
				rfl.add(rf);
			}
		} catch (Exception ex) {
			LogUtil.logException(ex, ReimbursementFormOracle.class);
		}
		return rfl;
	}

	@Override
	public List<ReimbursementForm> getFormsByEmp(int employeeId) {
		List<ReimbursementForm> rfl = new ArrayList<ReimbursementForm>();
		log.trace("Attempting to find ReimbursementForm with  Employee id = " + employeeId);
		String sql = "select id, employee, dateAndTime, locationPlace, descriptionAnecdote, costOfTraining, gradingFormat, " +
						"eventType, passingGrade, approval, urgency from ReimbursementForm where employee = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, employeeId);
			ResultSet rs = stmt.executeQuery();
			//log.trace(rs.next());
			while (rs.next()) {
				ReimbursementForm rf = new ReimbursementForm();
				rf.setId(rs.getInt("id"));
				rf.setEmployee(rs.getInt("employee"));
				rf.setDateAndTime(rs.getTimestamp("dateAndTime"));
				rf.setLocationPlace(rs.getString("locationPlace"));
				rf.setDescriptionAnecdote(rs.getString("descriptionAnecdote"));
				rf.setCostOfTraining(rs.getInt("costOfTraining"));
				rf.setGradingFormat(rs.getInt("gradingFormat"));
				rf.setEventType(rs.getInt("eventType"));
				rf.setPassingGrade(rs.getString("passingGrade"));
				rf.setApproval(rs.getInt("approval"));
				rf.setUrgency(rs.getBoolean("urgency"));
				rfl.add(rf);
			}
		} catch (Exception ex) {
			LogUtil.logException(ex, ReimbursementFormOracle.class);
		}
		return rfl;
	}

	@Override
	public void delete(int reimbursementFormid) {
		log.trace("deleting form from the database where form = " + reimbursementFormid);
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false); 
			String sql = "delete from ReimbursementForm where id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, reimbursementFormid);

			int result = pstm.executeUpdate();
			if (result == 1) {
				log.trace("form deleted.");
				conn.commit();
			} else {
				log.trace("form not deleted.");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, ReimbursementFormOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, ReimbursementFormOracle.class);
			}
		}
	}

}
