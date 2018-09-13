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
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Employees employee, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO Employee" +
				"(IdEmployee, EmployeeCode, Name, Password, Email, Gender, Phone, Address, EmployeePic, isDeleted, IdDepartment)" +
				"VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)";
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
				"EmployeeCode = ?, Name = ?, Password = ?, Email = ?, Gender = ?, " +
				"Phone = ?, Address = ?, EmployeePic = ?, IdDepartment = ?" +
				"WHERE IdEmployee = ?";
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
				"WHERE IdEmployee = ?";
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
		String sql = "SELECT EmployeePic FROM Employee WHERE IdEmployee = ?";
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
		String sql = "SELECT EmployeeCode, Name, Password, Email, Gender, Phone, Address, EmployeePic, isDeleted, IdDepartment " +
				"FROM Employee WHERE IdEmployee = ?";
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
				int isDeleted = rs.getInt(10), departmentId = rs.getInt(11);
				Blob image = rs.getBlob(10);
				employee = new Employees(id, code, name, password, email, gender, phone, address, departmentId);
				employee.setIsDeleted(isDeleted);
				employee.setImage(image);
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
		String sql = "SELECT * FROM Employee";
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
				int isDeleted = rs.getInt(10), departmentId = rs.getInt(11);
				Blob image = rs.getBlob(10);
				Employees employee = new Employees(id, code, name, password, email, gender, phone, address, departmentId);
				employee.setIsDeleted(isDeleted);
				employee.setImage(image);
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

}
