package service.event;

import java.util.List;

public interface EventDao {
	int insert(Events event, byte[] image);
	int update(Events event, byte[] image);
	int delete(int id);
	byte[] getImage(int id);
	Events findById(int id);
	List<Events> getAll();
	List<Events> getFive();
}
