package service.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import service.event.Events;
import service.roomtype.RoomType;

public class ReservationDaoMySqlImpl implements ReservationDao {

	public ReservationDaoMySqlImpl() {
		super();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Reservation reservation) {
		return 0;
	}

	@Override
	public int update(Reservation room) {
		return 0;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

//	@Override
//	public List<Reservation> findByRoomId(String checkInDate, String checkOutDate, int roomTypeId) {
//		String sql = "SELECT\n" + 
//				"rt.`IdRoomType`,\n" +  
//				"     COUNT(rrn.`IdRoomType`) quantity\n" + 
//				"  FROM RoomReservation rrn\n" + 
//				"       LEFT JOIN RoomType rt ON rrn.`IdRoomType` = rt.`IdRoomType`\n" + 
//				"       WHERE (rrn.`CheckInDate` between ? and ?) or (rrn.`CheckOuntDate` between '2018-09-23' and '2018-09-25') AND\n" + 
//				"       rrn.IdRoomType = ?\n" + 
//				"       GROUP BY rrn.`IdRoomType`";
//		Connection conn = null;
//		PreparedStatement ps = null;
//		List<RoomType> roomList = new ArrayList<RoomType>();
//		try {
//			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, roomTypeId);
//			ps.setString(2, checkInDate);
//			ps.setString(3, checkOutDate);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				int id = rs.getInt(1);
//				int quantity = rs.getInt(2);
//				RoomType room = new RoomType(id, quantity);
//				roomList.add(room);
//			}
//		} catch (SQLException e) {
//				e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return roomList;
//	}

	@Override
	public List<Reservation> getAll() {
		Connection connection = null;
		PreparedStatement ps = null;
		List<Reservation> reservationList = new ArrayList<Reservation>();
		return reservationList;
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
