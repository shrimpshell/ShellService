package service.roomtype;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import service.reservation.Reservation;

public class RoomTypeDaoMySqlImpl implements RoomTypeDao {

	public RoomTypeDaoMySqlImpl() {
		super();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(RoomType room, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO RoomType " + 
				"(IdRoomType, RoomTypeName, RoomSize, Bed, AdultQuantity, ChildQuantity, RoomQuantity, Price, RoomPic) " +
				"VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setString(2, room.getRoomSize());
			ps.setString(3, room.getBed());
			ps.setInt(4, room.getAdultQuantity());
			ps.setInt(5, room.getChildQuantity());
			ps.setInt(6, room.getRoomQuantity());
			ps.setInt(7, room.getPrice());
			ps.setBytes(8, image != null ? image : null);
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
	public int update(RoomType room, byte[] image) {
		int count = 0;
		String sql = "UPDATE RoomType SET RoomTypeName = ?, RoomSize = ?, Bed = ?, AdultQuantity = ?, ChildQuantity = ?, RoomQuantity = ?, Price = ?, RoomPic = ? WHERE IdRoomType = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setString(2, room.getRoomSize());
			ps.setString(3, room.getBed());
			ps.setInt(4, room.getAdultQuantity());
			ps.setInt(5, room.getChildQuantity());
			ps.setInt(6, room.getRoomQuantity());
			ps.setInt(7, room.getPrice());
			ps.setBytes(8, image != null ? image : null);
			ps.setInt(9, room.getId());
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
		String sql = "DELETE FROM RoomType WHERE IdRoomType = ?";
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
	public RoomType findById(int id) {
		String sql = "SELECT RoomTypeName, RoomSize, Bed, AdultQuantity, ChildQuantity, RoomQuantity, Price FROM RoomType WHERE IdRoomType = ?;";

		Connection conn = null;
		PreparedStatement ps = null;
		RoomType room = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
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
				room = new RoomType(0, name, roomSize, bed, adult, child, roomNum, price);
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
	public List<RoomType> getAll() {
		String sql = "SELECT * FROM RoomType ORDER BY `IdRoomType` ASC";
		Connection connection = null;
		PreparedStatement ps = null;
		List<RoomType> roomList = new ArrayList<RoomType>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String roomSize = rs.getString(3);
				String bed = rs.getString(4);
				int adult = rs.getInt(5);
				int child = rs.getInt(6);
				int roomNum = rs.getInt(7);
				int price = rs.getInt(8);
				RoomType room = new RoomType(id, name, roomSize, bed, adult, child, roomNum, price);
				roomList.add(room);
			}
			return roomList;
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
		return roomList;
	}

	@Override
	public List<RoomType> getReservation(String checkInDate, String checkOutDate) {
		String sql = "SELECT\n" + "rt.`IdRoomType`,\n" + " rt.`RoomTypeName`,\n" + "  rt.`RoomSize`,\n"
				+ "   rt.`Bed`,\n" + "    rt.`AdultQuantity`,\n" + "     rt.`ChildQuantity`,\n"
				+ "      COUNT(rrn.`IdRoomType`) quantity\n" + "  FROM RoomReservation rrn\n"
				+ "       LEFT JOIN RoomType rt ON rrn.`IdRoomType` = rt.`IdRoomType`\n"
				+ "       WHERE (rrn.`CheckInDate` between '" + checkInDate + "' and '" + checkOutDate
				+ "') or (rrn.`CheckOuntDate` between '" + checkInDate + "' and '" + checkOutDate + "') \n"
				+ "       GROUP BY rrn.`IdRoomType`\n" + "ORDER BY `IdRoomType` ASC";
		System.out.println(sql);
		Connection connection = null;
		PreparedStatement ps = null;
		List<RoomType> roomList = new ArrayList<RoomType>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String roomSize = rs.getString(3);
				String bed = rs.getString(4);
				int adult = rs.getInt(5);
				int child = rs.getInt(6);
				int roomNum = rs.getInt(7);
				RoomType room = new RoomType(id, name, roomSize, bed, adult, child, roomNum);
				roomList.add(room);
			}
			return roomList;
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
		return roomList;
	}
	
	@Override
	public List<RoomType> findByRoomId(String checkInDate, String checkOutDate, int roomTypeId) {
		String sql = "SELECT\n" + 
				"rt.`IdRoomType`,\n" +  
				"     COUNT(rrn.`IdRoomType`) quantity\n" + 
				"  FROM RoomReservation rrn\n" + 
				"       LEFT JOIN RoomType rt ON rrn.`IdRoomType` = rt.`IdRoomType`\n" + 
				"       WHERE (rrn.`CheckInDate` between ? and ?) or (rrn.`CheckOuntDate` between '2018-09-23' and '2018-09-25') AND\n" + 
				"       rrn.IdRoomType = ?\n" + 
				"       GROUP BY rrn.`IdRoomType`";
		Connection conn = null;
		PreparedStatement ps = null;
		List<RoomType> roomList = new ArrayList<RoomType>();
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomTypeId);
			ps.setString(2, checkInDate);
			ps.setString(3, checkOutDate);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				int quantity = rs.getInt(2);
				RoomType room = new RoomType(id, quantity);
				roomList.add(room);
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
		return roomList;
	}

	@Override
	public byte[] getImage(int id) {
		String sql = "SELECT RoomPic FROM RoomType WHERE IdRoomType = ?;";
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
		return image;
	}

	@Override
	public List<RoomType> getFive() {
		String sql = "SELECT * FROM RoomType ORDER BY IdRoomType DESC LIMIT 5";
		Connection connection = null;
		PreparedStatement ps = null;
		List<RoomType> roomList = new ArrayList<RoomType>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String roomSize = rs.getString(3);
				String bed = rs.getString(4);
				int adult = rs.getInt(5);
				int child = rs.getInt(6);
				int roomNum = rs.getInt(7);
				int price = rs.getInt(8);
				RoomType room = new RoomType(id, name, roomSize, bed, adult, child, roomNum, price);
				roomList.add(room);
			}
			return roomList;
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
		return roomList;
	}

}
