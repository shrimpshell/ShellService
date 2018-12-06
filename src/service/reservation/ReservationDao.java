package service.reservation;

import java.util.List;

import service.event.Events;

public interface ReservationDao {
	int insert(Reservation reservation);
	int update(Reservation reservation);
	int delete(int id);
	byte[] getImage(int id);
	List<Reservation> getAll();
}
