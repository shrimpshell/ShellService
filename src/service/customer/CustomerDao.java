package service.customer;

import java.util.List;

public interface CustomerDao {
	
	int userValid(String customerID, String password);

	boolean userExist(String email);

	int customerInsert(Customer customer);

	int update(Customer customer);

	int delete(int IdCustomer);
	
	int updateImage(Customer customer);

	Customer findById (int idCustomer);

	List<Customer> getAll();

}
