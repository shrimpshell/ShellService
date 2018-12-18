package service.customer.payDetail;

import java.util.List;

public interface PayDetailDao {

	List<Order> getPayDetailById(String userId);
	List<OrderRoomDetail> getRoomPayDetailById(String userId);
	List<OrderInstantDetail> getInstantPayDetail(String userId);
	List<OrderRoomDetail> viewRoomPayDetailByEmployee();
	List<OrderInstantDetail> viewInstantPayDetailByEmployee();
	String getDiscount();
	int updateRoomReservationStatusById(String roomReservationStatus, String idRoomReservation);
	List<OrderRoomDetail> getUserRoomNumber(String userId);

}
