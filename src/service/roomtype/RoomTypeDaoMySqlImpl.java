package service.roomtype;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import service.event.Events;

public class RoomTypeDaoMySqlImpl implements RoomTypeDao {

	public RoomTypeDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(RoomType room, byte[] image) {
		return 0;
	}

	@Override
	public int update(RoomType room, byte[] image) {
		return 0;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

	@Override
	public RoomType findById(int id) {
		String sql = "SELECT RoomTypeName, RoomSize, Bed, AdultQuantity, ChildQuantity, RoomQuantity, Price FROM RoomType WHERE IdRoomType = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		RoomType room = null;
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
		String sql = "SELECT * FROM RoomType";
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
	public byte[] getImage(int id) {
		String sql = "SELECT RoomPic FROM RoomType WHERE IdRoomType = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
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

}
