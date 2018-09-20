package service.event;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;

public class EventDaoMySqlImpl implements EventDao {
	

	public EventDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Events event, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO Events" +
		"(IdEvents, Events_Name, Events_Description, Events_Start_Datetime, Events_End_Datetime, Events_Pic, Discount)" +
		"VALUES (null, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			float discount = event.getDiscount() > 1.0f ? event.getDiscount()/10.0f : event.getDiscount();
			ps = connection.prepareStatement(sql);
			ps.setString(1, event.getName());
			ps.setString(2, event.getDescription());
			ps.setDate(3, Date.valueOf(event.getStart()));
			ps.setDate(4, Date.valueOf(event.getEnd()));
			ps.setBytes(5, image != null ? image : null);
			ps.setFloat(6, discount);
			count = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int update(Events event, byte[] image) {
		int count = 0;
		String sql = "UPDATE Events SET " +
				"Events_Name = ?, Events_Description = ?, Events_Start_Datetime = ?, Events_End_Datetime = ?, Events_Pic = ?, Discount = ? " +
				"WHERE IdEvents = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			float discount = event.getDiscount() > 1.0f ? event.getDiscount()/10.0f : event.getDiscount();
			ps = connection.prepareStatement(sql);
			ps.setString(1, event.getName());
			ps.setString(2, event.getDescription());
			ps.setDate(3, Date.valueOf(event.getStart()));
			ps.setDate(4, Date.valueOf(event.getEnd()));
			ps.setBytes(5, image != null ? image : null);
			ps.setFloat(6, discount);
			ps.setInt(7, event.getEventId());
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int delete(int id) {
		int count = 0;
		String sql = "DELETE FROM Events WHERE IdEvents = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public byte[] getImage(int id) {
		String sql = "SELECT Events_Pic FROM Events WHERE IdEvents = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	@Override
	public Events findById(int id) {
		String sql = "SELECT Events_Name, Events_Description, Events_Start_Datetime, Events_End_Datetime, Discount FROM Events WHERE IdEvents = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		Events event = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String name = rs.getString(2), description = rs.getString(5);
				Date start = rs.getDate(3), end = rs.getDate(4);
				float discount = rs.getFloat(8);
				@SuppressWarnings("deprecation")
				String startStr = start.getYear() + "-" + ((start.getMonth() + 1) < 10 ?  "0" + (start.getMonth() + 1) : (start.getMonth() + 1)) + start.getDate();
				@SuppressWarnings("deprecation")
				String endStr = end.getYear() + "-" + ((end.getMonth() + 1) < 10 ?  "0" + (end.getMonth() + 1) : (end.getMonth() + 1)) + end.getDate();
				event = new Events(id, name, description, startStr, endStr, discount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return event;
	}

	@Override
	public List<Events> getAll() {
		String sql = "SELECT * FROM Events";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Events> eventList = new ArrayList<Events>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2), description = rs.getString(5);
				Date start = rs.getDate(3), end = rs.getDate(4);
				float discount = rs.getFloat(8);
				@SuppressWarnings("deprecation")
				String startStr = (1900 + start.getYear()) + "-" + ((start.getMonth() + 1) < 10 ?  "0" + (start.getMonth() + 1) : (start.getMonth() + 1)) + "-" + start.getDate();
				@SuppressWarnings("deprecation")
				String endStr = (1900 + end.getYear()) + "-" + ((end.getMonth() + 1) < 10 ?  "0" + (end.getMonth() + 1) : (end.getMonth() + 1)) + "-" + end.getDate();
				Events event = new Events(id, name, description, startStr, endStr, discount);
				eventList.add(event);
			}
			return eventList;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eventList;
	}

	@Override
	public List<Events> getFive() {
		String sql = "SELECT * FROM Events ORDER BY IdEvents DESC LIMIT 5";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Events> eventList = new ArrayList<Events>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2), description = rs.getString(5);
				Date start = rs.getDate(3), end = rs.getDate(4);
				float discount = rs.getFloat(8);
				@SuppressWarnings("deprecation")
				String startStr = (1900 + start.getYear()) + "-" + ((start.getMonth() + 1) < 10 ?  "0" + (start.getMonth() + 1) : (start.getMonth() + 1)) + "-" + start.getDate();
				@SuppressWarnings("deprecation")
				String endStr = (1900 + end.getYear()) + "-" + ((end.getMonth() + 1) < 10 ?  "0" + (end.getMonth() + 1) : (end.getMonth() + 1)) + "-" + end.getDate();
				Events event = new Events(id, name, description, startStr, endStr, discount);
				eventList.add(event);
			}
			return eventList;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eventList;
	}

}
