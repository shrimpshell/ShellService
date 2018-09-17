package service.customer.payDetail;

import java.util.List;

public interface PayDetailDao {

	List<Order> getPayDetailById(String userId);
	int updateRoomReservationStatusById(String roomReservationStatus, String idRoomReservation);

}
