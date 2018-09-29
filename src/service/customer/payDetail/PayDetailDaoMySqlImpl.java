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
	public List<OrderRoomDetail> getRoomPayDetailById(String userId) {
		List<OrderRoomDetail> orderRoomDetailList = new ArrayList<>();
		OrderRoomDetail orderRoomDetail = null;
		String sql = "SELECT " + 
				"rReserv.IdRoomReservation, " + 
				"rReserv.CheckInDate, " + 
				"rReserv.CheckOuntDate, " + 
				"rStatus.RoomNumber, " + 
				"rType.Price, " + 
				"rType.RoomTypeName, " +
				"rReserv.roomQuantity, " +
				"rReserv.RoomReservationStatus, " +
				"rating.ratingStatus" +
				"FROM RoomReservation AS rReserv " + 
				"LEFT JOIN RoomType AS rType " + 
				"ON rReserv.IdRoomType = rType.IdRoomType " + 
				"LEFT JOIN RoomStatus AS rStatus " + 
				"ON rReserv.IdRoomReservation = rStatus.IdRoomReservation " + 
				"LEFT JOIN Rating AS rating " +
				"ON rReserv.IdRoomReservation = rating.IdRoomReservation" +
				"WHERE rReserv.IdCustomer = ? " + 
				"ORDER BY rReserv.CheckInDate DESC;";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idRoomReservation = rs.getInt(1);
				String checkInDate = Common.getFmtdDateToStr(rs.getDate(2));
				String checkOuntDate = Common.getFmtdDateToStr(rs.getDate(3));
				String roomNumber = rs.getString(4);
				String price = String.valueOf(rs.getInt(5));
				String roomTypeName = rs.getString(6);
				String roomQuantity = String.valueOf(rs.getInt(7));
				String roomReservationStatus = rs.getString(8);
				orderRoomDetail = new OrderRoomDetail(idRoomReservation, checkInDate, checkOuntDate, roomNumber, price, roomQuantity, roomTypeName, roomReservationStatus);
				orderRoomDetailList.add(orderRoomDetail);
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
		return orderRoomDetailList;
	}
	
	@Override
	public List<OrderInstantDetail> getInstantPayDetail(String userId) {
		List<OrderInstantDetail> orderInstantDetailList = new ArrayList<>();
		OrderInstantDetail orderInstantDetail = null;
		String sql = "SELECT " + 
				"iType.InstantTypeName, " + 
				"iDetail.Quantity, " + 
				"iType.InstantTypePrice AS InstantPrice, " +  
				"rReserv.RoomReservationStatus " + 
				"FROM RoomReservation AS rReserv " + 
				"LEFT JOIN RoomType AS rType " + 
				"ON rReserv.IdRoomType = rType.IdRoomType " + 
				"LEFT JOIN RoomStatus AS rStatus " + 
				"ON rReserv.IdRoomReservation = rStatus.IdRoomReservation " + 
				"LEFT JOIN InstantDetail AS iDetail " + 
				"ON rStatus.IdRoomStatus = iDetail.IdRoomStatus " + 
				"LEFT JOIN InstantType AS iType " + 
				"ON iDetail.IdInstantType = iType.IdInstantType " + 
				"WHERE rReserv.IdCustomer = ? " + 
				"ORDER BY rReserv.CheckInDate DESC;";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String diningTypeName = rs.getString(1);
				String quantity = String.valueOf(rs.getInt(2));
				String dtPrice = String.valueOf(rs.getInt(3));
				String roomReservationStatus = rs.getString(4);
				orderInstantDetail = new OrderInstantDetail(diningTypeName, quantity, dtPrice, roomReservationStatus);
				orderInstantDetailList.add(orderInstantDetail);
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
		return orderInstantDetailList;
	}

	@Override
	public List<Order> getPayDetailById(String userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Order> orderList = new ArrayList<>();
		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			String sql;
				sql = "SELECT " + 
						"rReserv.IdRoomReservation, " + 
						"rReserv.CheckInDate, " + 
						"rReserv.CheckOuntDate, " + 
						"rStatus.RoomNumber, " + 
						"rType.Price, " + 
						"iType.InstantTypeName, " + 
						"iDetail.Quantity, " + 
						"iType.InstantTypePrice AS InstantPrice, " +  
						"rReserv.RoomReservationStatus " + 
						"(SELECT Discount FROM RoomReservation,Events WHERE RoomReservation.CheckInDate BETWEEN Events_Start_Datetime AND Events_End_Datetime ORDER BY Discount ASC LIMIT 1) AS Discount" +
						"FROM RoomReservation AS rReserv " + 
						"LEFT JOIN RoomType AS rType " + 
						"ON rReserv.IdRoomType = rType.IdRoomType " + 
						"LEFT JOIN RoomStatus AS rStatus " + 
						"ON rReserv.IdRoomReservation = rStatus.IdRoomReservation " + 
						"LEFT JOIN InstantDetail AS iDetail " + 
						"ON rStatus.IdRoomStatus = iDetail.IdRoomStatus " + 
						"LEFT JOIN InstantType AS iType " + 
						"ON iDetail.IdInstantType = iType.IdInstantType " + 
						"WHERE rReserv.IdCustomer = ? " + 
						"ORDER BY rReserv.CheckInDate DESC;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userId);
		
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idRoomReservation = rs.getInt(1);
				String checkInDate = Common.getFmtdDateToStr(rs.getDate(2));
				String checkOuntDate = Common.getFmtdDateToStr(rs.getDate(3));
				String roomNumber = rs.getString(4);
				String price = String.valueOf(rs.getInt(5));
				String diningTypeName = rs.getString(6);
				String quantity = String.valueOf(rs.getInt(7));
				String dtPrice = String.valueOf(rs.getInt(8));
				String roomReservationStatus = rs.getString(9);
				String discount = String.valueOf(rs.getFloat(10));
				
				OrderDetail detail = new OrderDetail(roomNumber, price, diningTypeName, quantity, dtPrice, discount);
				orderDetailList.add(detail);
				Order order = new Order(idRoomReservation, checkInDate, checkOuntDate,roomReservationStatus,orderDetailList);
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
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
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

	@Override
	public String getDiscount() {
		String discount = "1";
		String sql = "SELECT Discount " +
				"FROM RoomReservation,Events " +
				"WHERE RoomReservation.CheckInDate BETWEEN Events_Start_Datetime AND Events_End_Datetime " +
				"ORDER BY Discount ASC LIMIT 1";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				discount = String.valueOf(rs.getFloat(1));
			}
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
		return discount;
	}
}
