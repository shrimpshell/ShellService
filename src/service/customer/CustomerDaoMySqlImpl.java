package service.customer;


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




public class CustomerDaoMySqlImpl implements CustomerDao {
	
	public CustomerDaoMySqlImpl() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int userValid(String email, String password) {
		String sql = "SELECT IdCustomer FROM Customer WHERE Email = ? AND Password = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int idCustomer = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idCustomer = rs.getInt(1);
				return idCustomer;
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
		return idCustomer;
	}
	

	@Override
	public boolean userExist(String email) {
		String sql = "SELECT Email FROM Customer WHERE Email = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		boolean isCustomerIdExist = false;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			isCustomerIdExist = rs.next();
			return isCustomerIdExist;
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
		return isCustomerIdExist;
	}

	@Override
	public int customerInsert(Customer customer) {
		String sql = "INSERT INTO Customer (CustomerID, Name, Email, Password, Gender, "
				+ "Birthday, Phone, Address) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, customer.getEmail());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getEmail());
			ps.setString(4, customer.getPassword());
			ps.setString(5, customer.getGender());
			ps.setDate(6, Date.valueOf(customer.getBirthday()));
			ps.setString(7, customer.getPhone());
			ps.setString(8, customer.getAddress());
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
	public int update(Customer customer) {
		String sql = "UPDATE Customer "
				+ "SET password = ?, phone = ?, address = ? "
				+ "WHERE idCustomer = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, customer.getPassword());
			ps.setString(2, customer.getPhone());
			ps.setString(3, customer.getAddress());
			ps.setInt(4, customer.getIdCustomer());
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
	public int delete(int idCustomer) {
		int count = 0;
		String sql = "UPDATE Customer SET " +
				"isDeleted = 1 " +
				"WHERE IdCustomer = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idCustomer);
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
	public Customer findById(int IdCustomer) {
		String sql = "SELECT Name, Email, Password, Birthday, Phone, Address "
				+ "FROM Customer WHERE IdCustomer = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Customer customer = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdCustomer);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String Name = rs.getString(1); 
				String Email = rs.getString(2);
				String Password = rs.getString(3);
				String Birthday = ChangeDate.getDateToStr(rs.getDate(4));
				String Phone = rs.getString(5);
				String Address = rs.getString(6);
				customer = new Customer(IdCustomer, Name, Email, Password, Birthday, Phone, Address);
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
		return customer;

	}

	@Override
	public List<Customer> getAll() {
		String sql = "SELECT * FROM Customer WHERE isDeleted = 0";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int IdCustomer = rs.getInt(1);
				String CustomerID = rs.getString(2);
				String Name = rs.getString(3);
				String Email = rs.getString(4);
				String Gender = rs.getString(5);
				String Birthday = ChangeDate.getDateToStr(rs.getDate(6));
				String Phone = rs.getString(7);
				String Address = rs.getString(8);
				Customer customer = new Customer(IdCustomer, CustomerID, Name, Email, Gender, 
						Birthday, Phone, Address);
				customerList.add(customer);
			}
			return customerList;
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
		return customerList;
	}

	@Override
	public int updateImage (int IdCustomer, byte[] image) {
		int count = 0;
		String sql = "UPDATE Customer "
				+ "SET CustomerPic = ? "
				+ "WHERE IdCustomer = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setBytes(1, image != null ? image : null);
			ps.setInt(2, IdCustomer);
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
	public byte[] getImage(int IdCustomer) {
		String sql = "SELECT CustomerPic FROM Customer WHERE IdCustomer = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdCustomer);
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
