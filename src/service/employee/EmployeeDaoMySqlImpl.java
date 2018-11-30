package service.employee;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;

public class EmployeeDaoMySqlImpl implements EmployeeDao {

	public EmployeeDaoMySqlImpl() {
		super();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Employees employee, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO Employee" +
				"(IdEmployee, EmployeeCode, Name, Password, Email, Gendar, Phone, Adress, EmployeePic, isDeleted, IdDepartment)" +
				"VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, employee.getCode());
			ps.setString(2, employee.getName());
			ps.setString(3, employee.getPassword());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getGender());
			ps.setString(6, employee.getPhone().length() > 0 ? employee.getPhone() : null);
			ps.setString(7, employee.getAddress().length() > 0 ? employee.getAddress() : null);
			ps.setBytes(8, image != null ? image : null);
			ps.setInt(9, employee.getDepartmentId());
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
	public int update(Employees employee, byte[] image) {
		int count = 0;
		String sql = "UPDATE Employee SET " +
				"EmployeeCode = ?, Name = ?, Password = ?, Email = ?, Gendar = ?, " +
				"Phone = ?, Adress = ?, EmployeePic = ?, IdDepartment = ? " +
				"WHERE IdEmployee = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, employee.getCode());
			ps.setString(2, employee.getName());
			ps.setString(3, employee.getPassword());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getGender());
			ps.setString(6, employee.getPhone());
			ps.setString(7, employee.getAddress());
			ps.setBytes(8, image != null ? image : null);
			ps.setInt(9, employee.getDepartmentId());
			ps.setInt(10, employee.getId());
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
	public int delete(int id) {
		int count = 0;
		String sql = "UPDATE Employee SET " +
				"isDeleted = 1 " +
				"WHERE IdEmployee = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
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
	public byte[] getImage(int id) {
		String sql = "SELECT EmployeePic FROM Employee WHERE IdEmployee = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
			System.out.println(image);
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
		return image;
	}

	@Override
	public Employees findById(int id) {
		String sql = "SELECT * FROM Employee WHERE IdEmployee = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Employees employee = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String code = rs.getString(2), name = rs.getString(3), 
						password = rs.getString(4), email = rs.getString(5), gender = rs.getString(6),
						phone = rs.getString(7), address = rs.getString(8);
				int departmentId = rs.getInt(11);
			
				employee = new Employees(id, code, name, password, email, gender, phone, address, departmentId);
				return employee;
			}
		} catch (Exception e) {
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
		return employee;
	}

	@Override
	public List<Employees> getAll() {

		String sql = "SELECT * FROM Employee WHERE isDeleted = 0 AND IdDepartment <> 5 ORDER BY IdEmployee DESC";

		List<Employees> employeeList = new ArrayList<Employees>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String code = rs.getString(2), name = rs.getString(3), 
						password = rs.getString(4), email = rs.getString(5), gender = rs.getString(6),
						phone = rs.getString(7), address = rs.getString(8);
				int departmentId = rs.getInt(11);
				Employees employee = new Employees(id, code, name, password, email, gender, phone, address, departmentId);
				employeeList.add(employee);
			}
			return employeeList;
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
		return employeeList;
	}

	@Override
	public boolean employeeValid(String email, String password) {
		String sql = "SELECT IdEmployee FROM Employee WHERE Email = ? AND Password = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		boolean isLogin = false;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			isLogin = rs.next();
			return isLogin;
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
		return isLogin;
	}

	@Override
	public int userValid(String email, String password) {
		String sql = "SELECT IdEmployee FROM Employee WHERE Email = ? AND Password = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		int idEmployee = 0;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idEmployee = rs.getInt(1);
				return idEmployee;
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
		return idEmployee;
	}

	@Override
	public boolean userExist(String email) {
		String sql = "SELECT Email FROM Employee WHERE Email = ? AND isDeleted = 0;";
		Connection connection = null;
		PreparedStatement ps = null;
		boolean isEmployeeIdExist = false;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			isEmployeeIdExist = rs.next();
			return isEmployeeIdExist;
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
		return isEmployeeIdExist;
	}

	@Override
	public int updateImage(int IdEmployee, byte[] image) {
		int count = 0;
		String sql = "UPDATE Employee SET EmployeePic = ? WHERE IdEmployee = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setBytes(1, image != null ? image : null);
			ps.setInt(2, IdEmployee);
			count = ps.executeUpdate();
			return count;
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
	public int updateWithoutImage(Employees employee) {
		int count = 0;
		String sql = "UPDATE Employee SET " +
				"EmployeeCode = ?, Name = ?, Password = ?, Email = ?, Gendar = ?, " +
				"Phone = ?, Adress = ?, IdDepartment = ? " +
				"WHERE IdEmployee = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, employee.getCode());
			ps.setString(2, employee.getName());
			ps.setString(3, employee.getPassword());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getGender());
			ps.setString(6, employee.getPhone());
			ps.setString(7, employee.getAddress());
			ps.setInt(8, employee.getDepartmentId());
			ps.setInt(9, employee.getId());
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

	

}
