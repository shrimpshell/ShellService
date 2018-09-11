package service.room;

import java.util.List;

public interface RoomDao {
	int insert(Room spot, byte[] image);

	int update(Room spot, byte[] image);

	int delete(int id);
	
	byte[] getImage(int id);

	Room findById(int id);

	List<Room> getAll();
}
