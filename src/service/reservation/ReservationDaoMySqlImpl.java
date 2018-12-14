package service.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;

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
	public Reservation insertReservation(Reservation reservation) {
		int reservationId = 0;
		int quantity = 0;
		Reservation roomReservation = null;
		Connection connection = null;
		String sql = "insert into RoomReservation(ReservationDate, CheckInDate, CheckOuntDate, " + 
		"ExtraBed, roomQuantity, RoomReservationStatus, IdCustomer, IdRoomType, IdEvents, RoomGroup, Price)\n" + 
				"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		PreparedStatement ps = null;
		System.out.println(sql);
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			// 寫入資料庫關閉，避免插入的時候報錯修，改的內容也不會提交到資料庫
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, reservation.getReservationDate());
			ps.setString(2, reservation.getCheckInDate());
			ps.setString(3, reservation.getCheckOutDate());
			ps.setInt(4, reservation.getExtraBed());
			ps.setInt(5, reservation.getQuantity());
			ps.setString(6, reservation.getReservationStatus());
			ps.setInt(7, reservation.getCustomerId());
			ps.setInt(8, reservation.getRoomTypeId());
			if(reservation.eventId != 0) {
				ps.setInt(9, reservation.getEventId());
			} else {
				ps.setNull(9, java.sql.Types.INTEGER);
			}
			ps.setString(10, reservation.getRoomGroup());
			ps.setInt(11, reservation.getPrice());
			ps.executeUpdate();
			// 回傳自動產生Id iOS 使用Alamofire會在reslut拿到
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				reservationId = rs.getInt(1);
				quantity = reservation.getQuantity();
				roomReservation = new Reservation(reservationId, quantity);
			}
			// 寫入資料庫
			connection.commit();
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
		return roomReservation;
		}

	public int insertRoomStatus(String roomNumber, int idRoomReservation) {
		int roomStatusId = 0;
		Connection connection = null;
		String sql = "insert into RoomStatus(RoomNumber, IdRoomReservation)\n" + 
				"values('" + roomNumber + "'," + idRoomReservation + ")";	
		PreparedStatement ps = null;
		System.out.println(sql);
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			// 寫入資料庫關閉，避免插入的時候報錯修，改的內容也不會提交到資料庫
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			// 回傳自動產生Id iOS 使用Alamofire會在reslut拿到
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				roomStatusId = rs.getInt(1);
			}
			// 寫入資料庫
			connection.commit();
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
		return roomStatusId;
		}
	
	@Override
	public String findRoomNumber(String checkInDate, String checkOutDate, int roomTypeId) {
		String sql = "select RoomNumber\n" + 
				"from Room\n" + 
				"where RoomNumber not in (select rs.RoomNumber\n" + 
				"from RoomStatus rs left join RoomReservation rr on rs.IdRoomReservation = rr.IdRoomReservation\n" + 
				"where rr.CheckInDate between '" + checkInDate + "' and '" + checkOutDate + "' or\n" + 
				"rr.CheckOuntDate between '" + checkInDate + "' and '" + checkOutDate + "') and IdRoomType = " + roomTypeId + "\n" + 
				"order by IdRoomType ASC LIMIT 0, 1";
		Connection connection = null;
		PreparedStatement ps = null;
		System.out.println(sql);
		String roomNumber = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				roomNumber = rs.getString(1);
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
		return roomNumber;
	}
}
