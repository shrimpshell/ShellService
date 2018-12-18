package service.instant;

import java.util.List;

public interface InstantDao {
	int insertInstant(Instant instant);
	int updateStatus(int idInstantDetail, int status);
	List<Instant> getEmployeeStatus(int idInstantService);
	List<Instant> getCustomerStatus(int idCustomer, String roomNumber);
	

}
