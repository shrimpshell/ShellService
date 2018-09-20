package service.instant;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.Common;
import service.employee.Employees;



public class InstantDaoMySqlImpl implements InstantDao {
	
	public InstantDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Instant instant) {
		int count = 0;
		String sql = "INSERT INTO InstantDetail" +
		"(IdInstantDetail, IdInstantService, Status, Quantity, IdInstantType, IdRoomStatus)" + 
				"VALUES(null, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, instant.getIdInstantDetail());
			ps.setInt(2, instant.getIdInstantService());
			ps.setInt(3, instant.getStatus());
			ps.setInt(4, instant.getQuantity());
			ps.setInt(5, instant.getIdInstantType());
			ps.setInt(6, instant.getIdRoomStatus());
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
	public int update(Instant instant) {
		int count = 0;
		String sql = "UPDATE InstantDetail SET Status =? WHERE IdInstantDetail = ?;";
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
		String sql = " SELECT * FROM InstantDetail; ";
		List<Instant> instantList = new ArrayList<Instant>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdInstantDetail = rs.getInt(1);
				int IdInstantService = rs.getInt(2);
				int Status = rs.getInt(3);
				int Quantity = rs.getInt(4);
				int IdInstantType = rs.getInt(5);
				int IdRoomStatus = rs.getInt(6);
				Instant instant = new Instant(IdInstantDetail, IdInstantService, Status, Quantity, IdInstantType, IdRoomStatus);
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
