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
			// 寫入資料庫關閉，避免插入的時候報錯修，改的內容也不會提交到資料庫
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
			ps.setInt(1, instant.getIdInstantService());
			ps.setString(2, instant.getRoomNumber());
			ps.setInt(3, instant.getStatus());
			ps.setInt(4, instant.getQuantity());
			ps.setInt(5, instant.getIdInstantType());
			ps.setInt(6, instant.getIdRoomStatus());
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
	public int updateStatus(int idInstantDetail, int status) {
		int count = 0;
		String sql = "UPDATE InstantDetail SET Status = ? WHERE IdInstantDetail = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);	
			ps.setInt(1, status);		
			ps.setInt(2, idInstantDetail);		
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
						IdInstantType = rs.getInt(6), IdRoomStatus = rs.getInt(7);		
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
	public List<Instant> getCustomerStatus(int idCustomer, String roomNumber) {
		String sql = "SELECT  inDetail.IdInstantDetail, inDetail.IdInstantService, inDetail.RoomNumber, inDetail.Status, inDetail.Quantity, inDetail.IdInstantType, inDetail.IdRoomStatus\n" + 
				"FROM RoomReservation AS rReserv \n" + 
				"    LEFT JOIN RoomType AS rType\n" + 
				"    ON rReserv.IdRoomType = rType.IdRoomType \n" + 
				"    LEFT JOIN RoomStatus AS rStatus\n" + 
				"    ON rReserv.IdRoomReservation = rStatus.IdRoomReservation \n" + 
				"    LEFT JOIN InstantDetail inDetail on rStatus.RoomNumber = inDetail.RoomNumber\n" + 
				"WHERE rReserv.IdCustomer = ? and rReserv.RoomReservationStatus = 1 and rReserv.RoomGroup = (select rr.RoomGroup\n" + 
				"from RoomReservation rr left join RoomStatus rs on rr.IdRoomReservation = rs.IdRoomReservation\n" + 
				"where rr.IdCustomer = ? and rs.RoomNumber = ? and rr.RoomReservationStatus = 1)\n" + 
				"ORDER BY rReserv.CheckInDate DESC";
		List<Instant> instantList = new ArrayList<Instant>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idCustomer);
			ps.setInt(2, idCustomer);
			ps.setString(3, roomNumber);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdInstantDetail = rs.getInt(1), IdInstantService = rs.getInt(2), Status = rs.getInt(4), Quantity = rs.getInt(5), 
						IdInstantType = rs.getInt(6), IdRoomStatus = rs.getInt(7);		
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
