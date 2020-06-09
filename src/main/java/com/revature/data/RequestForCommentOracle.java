package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.RequestForComment;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class RequestForCommentOracle implements RequestForCommentDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(RequestForCommentOracle.class);
	
	public RequestForComment addRequestForComment(RequestForComment requestForComment) {
		int key = 0;
		log.trace("adding requestForComment to the database: "+ requestForComment);
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "insert into RequestForComment(id, requestId, senderId, "
					+ "receiverId, commentText) values (?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, requestForComment.getId());
			pstm.setInt(2, requestForComment.getRequestId());
			pstm.setInt(3, requestForComment.getSenderId());
			pstm.setInt(4, requestForComment.getReceiverId());
			pstm.setString(5, requestForComment.getCommentText());			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			if(rs.next()) {
				log.trace("RequestForComment created");
				key = rs.getInt(1);
				requestForComment.setId(key);
				conn.commit();
			} else {
				log.trace("RequestForComment not created!");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, RequestForCommentOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				LogUtil.logException(e1, RequestForCommentOracle.class);
			}
		}
		return getRequestForComment(key);
	}

	public RequestForComment getRequestForComment(Integer id) {
		log.trace("Attempting to find RequestForComment with id = " + id);
		RequestForComment rfc = null;
		String sql = "select id, requestId, senderId, receiverId, commentText from RequestForComment where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found RequestForComment with id:  " + rs.getInt("id"));
				rfc = new RequestForComment();
				rfc.setId(rs.getInt("id"));
				rfc.setRequestId(rs.getInt("requestId"));
				rfc.setSenderId(rs.getInt("SenderId"));
				rfc.setReceiverId(rs.getInt("receiverId"));
				rfc.setCommentText(rs.getString("commentText"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, RequestForCommentOracle.class);
		}
		log.trace("getRequestForComment returning " + rfc);
		return rfc;
	}

	public void updateRequestForComment(RequestForComment requestForComment) {
		log.trace("Updating RequestForComment to " + requestForComment);
		Connection conn = null;
		try {
			conn = cu.getConnection();
			// JDBC Automatically commits all data unless
			// you tell it not to.
			conn.setAutoCommit(false);
			String sql = "update RequestForComment set commentText = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(2, requestForComment.getId());
			stmt.setString(1, requestForComment.getCommentText());
			int rs = stmt.executeUpdate();
			if (rs != 1) {
				log.warn("RequestForComment update failed.");
				conn.rollback();
				return;
			}
			log.info("RequestForComment updated!");
			conn.commit();
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, RequestForCommentOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, RequestForCommentOracle.class);
			}
		}
	}

	@Override
	public List<RequestForComment> getAllCommentsTo(Employee emp){
		List<RequestForComment> rfcl = new ArrayList<RequestForComment>();
		log.trace("retrieving all comments to: " + emp);
		try (Connection conn = cu.getConnection()) {
			String sql = "select id, requestId, senderId, receiverId, commentText from RequestForComment where receiverId = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getId());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RequestForComment rfc = new RequestForComment();
				rfc.setId(rs.getInt("id"));
				rfc.setRequestId(rs.getInt("requestId"));
				rfc.setSenderId(rs.getInt("senderId"));
				rfc.setReceiverId(rs.getInt("receiverId"));
				rfc.setCommentText(rs.getString("commentText"));
				rfcl.add(rfc);
			}
		} catch (Exception ex) {
			LogUtil.logException(ex, RequestForCommentOracle.class);
		}
		return rfcl;
	}

}
