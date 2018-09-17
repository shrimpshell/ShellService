package server.customer.rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.Common;



public class RatingDaoMySqlImpl implements RatingDao{
	
	public RatingDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Rating findById(int IdRoomReservation) {
		RoomReservation master = findMasterById(IdRoomReservation);
		RatingDetail detail = findDetailById(IdRoomReservation);
		Rating rating = new Rating(IdRoomReservation, master.getIdCustomer());
		return rating;
	}
	private RatingDetail findDetailById(int idRoomReservation) {
		// TODO Auto-generated method stub
		return null;
	}

	private RoomReservation findMasterById(int idRoomReservation) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int ratingInsert(Rating rating) {
		String sql = "INSERT INTO Rating (ratingStar, opinion, review, IdRoomReservation)"
				+ "VALUES(?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setFloat(1, rating.ratingStar);
			ps.setString(2, rating.opinion);
			ps.setString(3,  rating.review);
			ps.setInt(4, rating.IdRoomReservation);
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
	public int updateOpinion(Rating rating) {
		String sql = "UPDATE Rating "
				+ "SET ratingStar = ?, opinion = ? WHERE IdRating = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setFloat(1, rating.getRatingStar());
			ps.setString(2, rating.getOpinion());
			ps.setInt(3, rating.getIdRating());
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
	public int updateReview(Rating rating) {
		String sql = "UPDATE Rating "
				+ "SET Review = ? WHERE IdRating = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, rating.getReview());
			ps.setInt(2, rating.getIdRating());
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
	public int delete(int IdRating) {
		int count = 0;
		String sql = "DELETE FROM Rating WHERE IdRating = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRating);
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
	public List<Rating> getAll(int IdCustomer) {
		String sql = "Select C.ratingStar, C.opinion, C.review, C.IdRoomReservation From Customer as A, RoomReservation as B, Rating as C WHERE (A.IdCustomer = ?) And (A.IdCustomer = B.IdCustomer) "
				+ "And (B.IdRoomReservation = C.IdRoomReservation)";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Rating> ratingList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  IdCustomer);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Float ratingStar = rs.getFloat(1);
				String opinion = rs.getString(2);
				String  review = rs.getString(3);
				int IdRoomReservation = rs.getInt(4);
				Rating rating = new Rating(ratingStar, opinion, review, IdRoomReservation, IdCustomer);
				ratingList.add(rating);
			}
			return ratingList;
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
		return ratingList;
	}




	

}
