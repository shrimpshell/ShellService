package service.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import service.event.Events;

public class ReservationDaoMySqlImpl implements ReservationDao {

	public ReservationDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Reservation room, byte[] image) {
		return 0;
	}

	@Override
	public int update(Reservation room, byte[] image) {
		return 0;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

	@Override
	public Reservation findById(int id) {
		String sql = "SELECT RoomTypeName, RoomSize, Bed, AdultQuantity, ChildQuantity, RoomQuantity, Price FROM RoomType WHERE IdRoomType = ?;";

		Connection conn = null;
		PreparedStatement ps = null;
		Reservation room = null;
		return room;
	}

	@Override
	public List<Reservation> getAll() {
		Connection connection = null;
		PreparedStatement ps = null;
		List<Reservation> roomList = new ArrayList<Reservation>();
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

}
