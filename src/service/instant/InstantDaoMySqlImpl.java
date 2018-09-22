package service.instant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;


public class InstantDaoMySqlImpl implements InstantDao{

	@Override
	public int insertInstant(Instant instant) {
		int count = 0;
		String sql = "";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, instant.getIdInstantDetail());
			ps.setInt(2, instant.getIdInstantService());
			ps.setString(3, instant.getRoomNumber());
			ps.setInt(4, instant.getStatus());
			ps.setInt(5, instant.getQuantity());
			ps.setInt(6, instant.getIdInstantType());
			ps.setInt(7, instant.getIdRoomStatus());
			count = ps.executeUpdate();
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
		return count;
		}

	@Override
	public int updateStatus(Instant instant) {
		int count = 0;
		String sql = "UPDATE InstantDetail SET " +
				"Status = ?" +
				"WHERE IdInstantDetail = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, instant.getStatus());
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
	public List<Instant> getAll() {
		String sql = "SELECT * FROM InstantDetail ;";
		List<Instant> instantList = new ArrayList<Instant>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdInstantDetail = rs.getInt(1), IdInstantService= rs.getInt(2), 
						Status = rs.getInt(3), Quantity = rs.getInt(4), IdInstantType = rs.getInt(5), 
						IdRoomStatus = rs.getInt(6);
				String RoomNumber = rs.getString(7);
				Instant instant = new Instant(IdInstantDetail, IdInstantService, Status, Quantity, 
						IdInstantType, IdRoomStatus, RoomNumber);
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
