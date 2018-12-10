package service.reservation;

import java.util.List;

import service.event.Events;

public interface ReservationDao {
	int insertReservation(Reservation reservation);
	int insertRoomStatus(String roomNumber, int customerId);
	List<Reservation> getAll();
	public String findRoomNumber(String checkInDate, String checkOutDate, int roomTypeId);
}
