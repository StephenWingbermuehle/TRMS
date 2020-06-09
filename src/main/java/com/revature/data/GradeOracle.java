package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Grade;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class GradeOracle implements GradeDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(GradeOracle.class);
	
	public Grade getGrade(Integer id) {
		log.trace("Attempting to find grade with id = " + id);
		Grade g = null;
		String sql = "select id, gradeType from Grade where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found grade with id:  " + rs.getInt("id"));
				g = new Grade();
				g.setId(rs.getInt("id"));
				g.setGradeType(rs.getString("gradeType"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, GradeOracle.class);
		}
		log.trace("getGrade returning " + g);
		return g;

	}

	@Override
	public List<Grade> getAll() {
		List<Grade> gradeList = new ArrayList<Grade>();
		log.trace("retrieving gradeTypes from database");
		try (Connection conn = cu.getConnection()) {
			String sql = "select id, gradeType, requirePresentation from Grade";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Grade g = new Grade();
					g.setId(rs.getInt("id"));
					g.setGradeType(rs.getString("gradeType"));
					g.setRequirePresentation(rs.getBoolean("requirePresentation"));
					gradeList.add(g);
			}
		} catch (Exception e) {
			LogUtil.logException(e, GradeOracle.class);
		}
		return gradeList;
	}

}
