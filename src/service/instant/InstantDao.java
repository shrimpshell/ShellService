package service.instant;

import java.util.List;

public interface InstantDao {
	
	int insert(Instant instant);
	
	int update(Instant instant); 
	
	Instant findById (int id);
	
	List<Instant> getAll();
	
	
}
