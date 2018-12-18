package service.customer;
import service.reservation.*;
import java.util.List;

public interface CustomerDao {
	
	int userValid(String customerID, String password);

	boolean userExist(String email);

	int customerInsert(Customer customer);

	int update(Customer customer);

	int delete(int IdCustomer);

	Customer findById (int idCustomer);
	
	public byte[] getImage(int IdCustomer);

	List<Customer> getAll();

	int updateImage(int IdCustomer, byte[] image);

	Customer getRoomReservationStatus(int IdCustomer);

}
