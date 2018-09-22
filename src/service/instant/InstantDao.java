package service.instant;

import java.util.List;

public interface InstantDao {
	int insertInstant(Instant instant);
	int updateStatus(Instant instant);
	List<Instant> getAll();

}
