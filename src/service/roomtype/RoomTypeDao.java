package service.roomtype;

import java.util.List;

import service.reservation.Reservation;

public interface RoomTypeDao {
	int insert(RoomType room, byte[] image);
	int update(RoomType room, byte[] image);
	int delete(int id);
	byte[] getImage(int id);
	RoomType findById(int id);
	List<RoomType> getAll();
	List<RoomType> getReservation(String checkInDate,String checkOutDate);
	List<RoomType> findByRoomId(String checkInDate, String checkOutDate, int roomTypeId);
	List<RoomType> getFive();
}
