package service.customer.payDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;

public class PayDetailDaoMySqlImpl implements PayDetailDao {

	public PayDetailDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getPayDetailById(String userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Order> orderList = new ArrayList<>();
		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			String sql;
				sql = "select " + 
						"rr.IdRoomReservation, " + 
						"rr.CheckInDate, " + 
						"rr.CheckOuntDate," + 
						"rs.RoomNumber, " + 
						"rs.RoomLevel, " + 
						"rs.RoomType, " + 
						"rt.Price," + 
						"dt.DiningTypeName, " + 
						"ds.Quantity, " + 
						"dt.Price as dtPrice, " + 
						"( select Discount from RoomReservation,Events Where RoomReservation.CheckInDate between Events_Start_Datetime and Events_End_Datetime) AS Discount, "+ 
						"rr.RoomReservationStatus " + 
						"from RoomReservation as rr " + 
						"left join RoomType as rt " + 
						"on rr.IdRoomType = rt.IdRoomType " + 
						"left join RoomStatus as rs " + 
						"on rr.IdRoomReservation = rs.IdRoomReservation " + 
						"left join DiningService as ds " + 
						"on rs.IdRoomStatus = ds.IdRoomStatus " + 
						"left join DiningType as dt " + 
						"on ds.IdDiningType = dt.IdDiningType " + 
						"where rr.IdCustomer = ? " + 
						"order by rr.CheckInDate ASC;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userId);
		
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idRoomReservation = rs.getInt(1);
				String checkInDate = Common.getFmtdDateToStr(rs.getDate(2));
				String checkOuntDate = Common.getFmtdDateToStr(rs.getDate(3));
				String roomNumber = rs.getString(4);
				String roomLevel = rs.getString(5);
				String roomType = rs.getString(6);
				String price = String.valueOf(rs.getInt(7));
				String diningTypeName = rs.getString(8);
				String quantity = String.valueOf(rs.getInt(9));
				String dtPrice = String.valueOf(rs.getInt(10));
				String discount = String.valueOf(rs.getFloat(11));
				String roomReservationStatus = rs.getString(12);
				
				OrderDetail detail = new OrderDetail(roomNumber, roomLevel, roomType, price, diningTypeName,
						quantity, dtPrice, discount);
				orderDetailList.add(detail);
				Order order = new Order(idRoomReservation, checkInDate,
						checkOuntDate,roomReservationStatus,orderDetailList);
				orderList.add(order);
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
		return orderList;
	}

	@Override
	public int updateRoomReservationStatusById(String roomReservationStatus, String idRoomReservation ) {
		int count = 0;
		String sql = "UPDATE RoomReservation SET RoomReservationStatus = ? WHERE idRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, roomReservationStatus);
			ps.setInt(2, Integer.parseInt(idRoomReservation));
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
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
	
}
