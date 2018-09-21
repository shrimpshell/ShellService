package server.customer.rating;

import java.util.List;


public interface RatingDao {

	int ratingInsert(Rating rating);

	int updateOpinion(Rating rating);
	
	int updateReview(Rating rating);
	
	int delete(int IdRoomReservation);

	Rating findById (int IdRoomReservation);

	List<Rating> getAll(int IdCustomer);
	
	List<Rating> getAll();

	



	

}
