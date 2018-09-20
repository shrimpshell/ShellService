package server.cleanservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Common;

public class CleanServiceDaoMySqlImpl implements CleanServiceDao {
	
	public CleanServiceDaoMySqlImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int getLastCleanServiceId(int idRoomStatus) {
		int serviceId = 0;
		String sql = "SELECT * FROM CleanService WHERE IdRoomStatus = ? AND Status <> 3 ORDER BY IdCleanService DESC LIMIT 1";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idRoomStatus);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				serviceId = rs.getInt(1);
				return serviceId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceId;
	}

	@Override
	public int insertCleanStatus(int IdRoomStatus) {
		int count = 0;
		String sql = "INSERT INTO CleanService (IdHousekeeping, Status, IdRoomStatus) VALUES (null, 1, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRoomStatus);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public int updateCleanStatus(int IdRoomStatus, int status) {
		int count = 0;
		String sql = "UPDATE CleanService SET Status = ? WHERE IdHousekeeping = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, IdRoomStatus);
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
	public int getIdFromRoomNumber(String roomNumber) {
		int IdRoomStatus = 0;
		String sql = "SELECT IdRoomStatus FROM RoomStatus WHERE RoomNumber = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, roomNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				IdRoomStatus = rs.getInt(1);
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
		return IdRoomStatus;
	}

}
