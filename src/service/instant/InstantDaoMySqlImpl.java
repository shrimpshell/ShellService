package service.instant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.Common;


public class InstantDaoMySqlImpl implements InstantDao{

	@Override
	public int insertInstant(Instant instant) {
		int IdInstantDetail = 0;
		Connection connection = null;
		String sql = "INSERT INTO InstantDetail "
				+ "(IdInstantService, RoomNumber, Status ,Quantity, IdInstantType, IdRoomStatus)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";	
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
			ps.setInt(1, instant.getIdInstantService());
			ps.setString(2, instant.getRoomNumber());
			ps.setInt(3, instant.getStatus());
			ps.setInt(4, instant.getQuantity());
			ps.setInt(5, instant.getIdInstantType());
			ps.setInt(6, instant.getIdRoomStatus());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				IdInstantDetail = rs.getInt(1);
			}
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
	public int updateStatus(int idInstantDetail, int status) {
		int count = 0;
		String sql = "UPDATE InstantDetail SET Status = ? WHERE IdInstantDetail = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			System.out.println("TEST3: " + ps);
			ps.setInt(1, status);
			System.out.println("TEST4: " + ps);
			ps.setInt(2, idInstantDetail);
			System.out.println("TEST5: " + ps);
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
	public List<Instant> getEmployeeStatus(int idInstantService) {
		List<Instant> instantList = new ArrayList<Instant>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			String sql;
			if (idInstantService == 1) {
				sql = "SELECT * FROM db_cp102b.InstantDetail WHERE IdInstantService = 1 ORDER BY IdInstantDetail DESC;";
				ps = connection.prepareStatement(sql);
			} else if (idInstantService == 2) {
				sql = "SELECT * FROM db_cp102b.InstantDetail WHERE IdInstantService = 2 ORDER BY IdInstantDetail DESC;";
				ps = connection.prepareStatement(sql);
			} else if (idInstantService == 3) {
				sql = "SELECT * FROM db_cp102b.InstantDetail WHERE IdInstantService = 3 ORDER BY IdInstantDetail DESC;";
				ps = connection.prepareStatement(sql);
			} 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdInstantDetail = rs.getInt(1), IdInstantService = rs.getInt(2), Status = rs.getInt(4), Quantity = rs.getInt(5), 
						IdInstantType = rs.getInt(6), IdRoomStatus = rs.getInt(6);		
				String RoomNumber = rs.getString(3);
				Instant instant = new Instant
				(IdInstantDetail, IdInstantService, RoomNumber, Status, Quantity, IdInstantType, IdRoomStatus);
				instantList.add(instant);
			}
			return instantList;
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
		return instantList;
	}

	@Override
	public List<Instant> getCustomerStatus(String roomNumber) {
		String sql = "SELECT IdInstantDetail, IdInstantService, RoomNumber, Status, Quantity, IdInstantType "
				+ "FROM db_cp102b.InstantDetail "
				+ "WHERE Status <>1 AND RoomNumber = ? ORDER BY IdInstantDetail DESC;";
		List<Instant> instantList = new ArrayList<Instant>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, roomNumber);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdInstantDetail = rs.getInt(1), IdInstantService = rs.getInt(2), Status = rs.getInt(4), Quantity = rs.getInt(5), 
						IdInstantType = rs.getInt(6), IdRoomStatus = rs.getInt(6);		
				String RoomNumber = rs.getString(3);
				Instant instant = new Instant
				(IdInstantDetail, IdInstantService, RoomNumber, Status, Quantity, IdInstantType, IdRoomStatus);
				instantList.add(instant);
			}
			return instantList;
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
		return instantList;
	}

}
