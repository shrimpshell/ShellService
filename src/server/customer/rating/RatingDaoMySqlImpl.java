package server.customer.rating;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ChangeDate;
import common.Common;




public class RatingDaoMySqlImpl implements RatingDao{
	
	public RatingDaoMySqlImpl() {
		super();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Rating findById(int IdRoomReservation) {
		String sql = "SELECT ratingStar, opinion"
				+ "FROM Rating WHERE IdRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Rating rating= null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRoomReservation);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Float ratingStar = rs.getFloat(1); 
				String opinion = rs.getString(2);
		 rating = new Rating(IdRoomReservation, ratingStar, opinion);
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
		return rating;
	}
	
	@Override
	public int getRatingStatus(int IdRoomReservation) {
		String sql = "SELECT ratingStatus FROM Rating WHERE IdRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int ratingStatus = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRoomReservation);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ratingStatus = rs.getInt(1);
				return ratingStatus;
			}
		}catch (SQLException e) {
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
		return ratingStatus;
	}

	@Override
	public int ratingInsert(Rating rating) {
		String sql = "INSERT INTO Rating (ratingStar, time, opinion, review, ratingStatus, IdRoomReservation)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setFloat(1, rating.getRatingStar());
			ps.setDate(2,  Date.valueOf(rating.getTime()));
			ps.setString(3, rating.getOpinion());
			ps.setString(4,  rating.getReview());
			ps.setInt(5,  rating.getRatingStatus());
			ps.setInt(6, rating.getIdRoomReservation());
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
				+ "SET ratingStar = ?, opinion = ? WHERE IdRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setFloat(1, rating.getRatingStar());
			ps.setString(2, rating.getOpinion());
			ps.setInt(3, rating.getIdRoomReservation());
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
				+ "SET Review = ?, ratingStatus = ? WHERE IdRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, rating.getReview());
			ps.setInt(2, rating.getRatingStatus());
			ps.setInt(3, rating.getIdRoomReservation());
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
	public int delete(int IdRoomReservation) {
		int count = 0;
		String sql = "UPDATE Rating "
				+ "SET ratingStatus = 3 WHERE IdRoomReservation = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRoomReservation);
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

//	@Override
	public List<Rating> getAllById(int IdCustomer) {
		String sql = "Select C.ratingStar, C.time, C.opinion, C.review, C.IdRoomReservation, C.ratingStatus From Customer as A, RoomReservation as B, Rating as C "
				+ "WHERE (A.IdCustomer = ?) And (A.IdCustomer = B.IdCustomer) "
				+ "And (B.IdRoomReservation = C.IdRoomReservation)" 
				+ "ORDER BY time DESC, IdRating DESC";
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
				String time = ChangeDate.getDateToStr(rs.getDate(2));
				String opinion = rs.getString(3);
				String  review = rs.getString(4);
				int IdRoomReservation = rs.getInt(5);
				int ratingStatus = rs.getInt(6);
				Rating rating = new Rating(ratingStatus, ratingStar, time, opinion, review, IdRoomReservation, IdCustomer);
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

	@Override
	public List<Rating> getAll() {
		String sql = "Select Rating.ratingStar, Rating.time, Rating.opinion, Rating.review, Rating.ratingStatus, "
				+ "Rating.IdRoomReservation, Customer.Name From Rating join RoomReservation on "
				+ "Rating.IdRoomReservation=RoomReservation.IdRoomReservation join Customer on "
				+ "RoomReservation.IdCustomer=Customer.IdCustomer WHERE Rating.ratingStatus <> 3 ORDER BY time DESC, IdRating DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Rating> ratingList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
				
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Float ratingStar = rs.getFloat(1);
				String time = ChangeDate.getDateToStr(rs.getDate(2));
				String opinion = rs.getString(3);
				String  review = rs.getString(4);
				int ratingStatus = rs.getInt(5);
				int IdRoomReservation = rs.getInt(6);
				String Name = rs.getString(7);
				Rating rating = new Rating(ratingStar, time, opinion, review, ratingStatus, IdRoomReservation, Name);
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
	
	
	@Override
	public List<Rating> getAllByHighRatingStar() {
		String sql = "Select Rating.ratingStar, Rating.time, Rating.opinion, Rating.review, Rating.ratingStatus,"
				+ "Rating.IdRoomReservation, Customer.Name From Rating join RoomReservation on "
				+ "Rating.IdRoomReservation=RoomReservation.IdRoomReservation join Customer on "
				+ "RoomReservation.IdCustomer=Customer.IdCustomer WHERE Rating.ratingStatus <> 3 ORDER BY ratingStar DESC, IdRating DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Rating> ratingList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
				
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Float ratingStar = rs.getFloat(1);
				String time = ChangeDate.getDateToStr(rs.getDate(2));
				String opinion = rs.getString(3);
				String  review = rs.getString(4);
				int ratingStatus = rs.getInt(5);
				int IdRoomReservation = rs.getInt(6);
				String Name = rs.getString(7);
				Rating rating = new Rating(ratingStar, time, opinion, review, ratingStatus, IdRoomReservation, Name);
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

	@Override
	public List<Rating> getAllByLowRatingStar() {
		String sql = "Select Rating.ratingStar, Rating.time, Rating.opinion, Rating.review, Rating.ratingStatus,"
				+ "Rating.IdRoomReservation, Customer.Name From Rating join RoomReservation on "
				+ "Rating.IdRoomReservation=RoomReservation.IdRoomReservation join Customer on "
				+ "RoomReservation.IdCustomer=Customer.IdCustomer WHERE Rating.ratingStatus <> 3 ORDER BY ratingStar ASC, IdRating DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Rating> ratingList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
				
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Float ratingStar = rs.getFloat(1);
				String time = ChangeDate.getDateToStr(rs.getDate(2));
				String opinion = rs.getString(3);
				String  review = rs.getString(4);
				int ratingStatus = rs.getInt(5);
				int IdRoomReservation = rs.getInt(6);
				String Name = rs.getString(7);
				Rating rating = new Rating(ratingStar, time, opinion, review, ratingStatus, IdRoomReservation, Name);
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

	@Override
	public List<Rating> getAllByRatingStatus() {
		String sql = "Select Rating.ratingStar, Rating.time, Rating.opinion, Rating.review, Rating.ratingStatus,"
				+ "Rating.IdRoomReservation, Customer.Name From Rating join RoomReservation on "
				+ "Rating.IdRoomReservation=RoomReservation.IdRoomReservation join Customer on "
				+ "RoomReservation.IdCustomer=Customer.IdCustomer WHERE Rating.ratingStatus <> 3 ORDER BY ratingStatus ASC, IdRating DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Rating> ratingList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
				
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Float ratingStar = rs.getFloat(1);
				String time = ChangeDate.getDateToStr(rs.getDate(2));
				String opinion = rs.getString(3);
				String  review = rs.getString(4);
				int ratingStatus = rs.getInt(5);
				int IdRoomReservation = rs.getInt(6);
				String Name = rs.getString(7);
				Rating rating = new Rating(ratingStar, time, opinion, review, ratingStatus, IdRoomReservation, Name);
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
