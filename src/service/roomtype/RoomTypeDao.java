package service.roomtype;

import java.util.List;

import service.event.Events;

public interface RoomTypeDao {
	int insert(RoomType spot, byte[] image);
	int update(RoomType spot, byte[] image);
	int delete(int id);
	byte[] getImage(int id);
	RoomType findById(int id);
	List<RoomType> getAll();
	List<RoomType> getAll(String checkInDate,String checkOutDate);
}
