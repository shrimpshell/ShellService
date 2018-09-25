package service.reservation;

import java.util.List;

import service.event.Events;

public interface ReservationDao {
	int insert(Reservation room, byte[] image);
	int update(Reservation room, byte[] image);
	int delete(int id);
	byte[] getImage(int id);
	Reservation findById(int id);
	List<Reservation> getAll();
}
