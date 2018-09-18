package service.employee;

import java.util.List;

public interface EmployeeDao {
	
	int userValid(String email, String password);

	boolean userExist(String email);
	
	boolean employeeValid(String employCode, String password);
	
	int insert(Employees employee, byte[] image);
	
	int update(Employees employee, byte[] image);
	
	int delete(int id);
	
	byte[] getImage(int id);
	
	Employees findById(int id);
	
	List<Employees> getAll();
}
