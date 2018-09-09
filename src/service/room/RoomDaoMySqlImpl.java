package service.room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;

public class RoomDaoMySqlImpl implements RoomDao {

	public RoomDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Room room, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO RoomType" + 
				"(IdRoomType, RoomTypeName, RoomSize, Bed, AdultQuantity, ChildQuantity, RoomQuantity, Price)" +
				"VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setString(2, room.getRoomSize());
			ps.setString(3, room.getBed());
			ps.setInt(4, room.getAdultQuantity());
			ps.setInt(5, room.getChildQuantity());
			ps.setInt(6, room.getRoomQuantity());
			ps.setInt(7, room.getPrice());
			count = ps.executeUpdate();
		} catch (SQLException e) {
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
	public int update(Room room, byte[] image) {
		int count = 0;
		String sql = "UPDATE RoomType SET RoomTypeName = ?, RoomSizse = ?, Bed = ?, AdultQuantity = ?, ChildQuantity = ?, RoomQuantity = ?, Price = ? WHERE IdRoomType = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setString(2, room.getRoomSize());
			ps.setString(3, room.getBed());
			ps.setInt(4, room.getAdultQuantity());
			ps.setInt(5, room.getChildQuantity());
			ps.setInt(6, room.getRoomQuantity());
			ps.setInt(7, room.getPrice());
			ps.setInt(8, room.getId());
			count = ps.executeUpdate();
		} catch (SQLException e) {
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
		String sql = "DELETE FROM RoomType WHERE IdRoomType = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
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
	public Room findById(int id) {
		String sql = "SELECT RoomTypeName, Detail, AdultQuantity, ChildQuantity, RoomQuantity, Price FROM RoomType WHERE IdRoomType = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Room room = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String name = rs.getString(1);
				String roomSize = rs.getString(2);
				String bed = rs.getString(3);
				int adult = rs.getInt(4);
				int child = rs.getInt(5);
				int roomNum = rs.getInt(6);
				int price = rs.getInt(7);
				room = new Room(0, name, roomSize, bed, adult, child, roomNum, price);
			}
		} catch (SQLException e) {
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
		return room;
	}

	@Override
	public List<Room> getAll() {
		String sql = "SELECT id, name, phoneNo, address, latitude, longitude "
				+ "FROM Spot ORDER BY timestamp DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Room> spotList = new ArrayList<Room>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String roomSize = rs.getString(2);
				String bed = rs.getString(3);
				int adult = rs.getInt(4);
				int child = rs.getInt(5);
				int roomNum = rs.getInt(6);
				int price = rs.getInt(7);
				Room room = new Room(0, name, roomSize, bed, adult, child, roomNum, price);
				spotList.add(room);
			}
			return spotList;
		} catch (SQLException e) {
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
		return spotList;
	}

}
