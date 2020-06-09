package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.EventType;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class EventTypeOracle implements EventTypeDAO{
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(EventTypeOracle.class);
	
	public EventType getEventType(Integer id) {
		log.trace("Attempting to find EventType with id = " + id);
		EventType et = null;
		String sql = "select id, eventType, percentCoverage from EventType where id = ?";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.trace("Found EventType with id:  " + rs.getInt("id"));
				et = new EventType();
				et.setId(rs.getInt("id"));
				et.setEventType(rs.getString("eventType"));
				et.setPercentCoverage(rs.getDouble("percentCoverage"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, EventTypeOracle.class);
		}
		log.trace("getEventType returning " + et);
		return et;
	}

	@Override
	public List<EventType> getAll() {
		List<EventType> eventList = new ArrayList<EventType>();
		log.trace("retrieving eventTypes from database");
		try (Connection conn = cu.getConnection()) {
			String sql = "select id, eventType, percentCoverage from EventType";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			//log.trace(rs.next());
			while (rs.next()) {
				EventType et = new EventType();
					et.setId(rs.getInt("id"));
					et.setEventType(rs.getString("eventType"));
					log.trace(rs.getString("eventType"));
					et.setPercentCoverage(rs.getDouble("percentCoverage"));
					eventList.add(et);
			}
		} catch (Exception e) {
			LogUtil.logException(e, EventTypeOracle.class);
		}
		// TODO Auto-generated method stub
		return eventList;
	}

}
