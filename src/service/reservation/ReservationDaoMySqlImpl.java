package service.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import service.event.Events;
import service.instant.Instant;
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
	public int insertReservation(Reservation reservation) {
		int IdInstantDetail = 0;
		Connection connection = null;
		String sql = "insert into RoomReservation(ReservationDate, CheckInDate, CheckOuntDate, " + 
		"ExtraBed, roomQuantity, RoomReservationStatus, IdCustomer, IdRoomType, IdEvents, RoomGroup, Price)\n" + 
				"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		PreparedStatement ps = null;
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
			ps.setInt(9, reservation.getEventId());
			ps.setString(10, reservation.getRoomGroup());
			ps.setInt(11, reservation.getPrice());
			ps.executeUpdate();
			// 回傳自動產生Id iOS 使用Alamofire會在reslut拿到
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				IdInstantDetail = rs.getInt(1);
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
		return IdInstantDetail;
		}

	@Override
	public int update(Reservation room) {
		return 0;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

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
