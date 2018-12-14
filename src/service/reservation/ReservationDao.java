package service.reservation;

import java.util.List;

public interface ReservationDao {
	Reservation insertReservation(Reservation reservation);
	int insertRoomStatus(String roomNumber, int customerId);
	public String findRoomNumber(String checkInDate, String checkOutDate, int roomTypeId);
}
