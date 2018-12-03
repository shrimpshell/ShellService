package server.customer.rating;

import java.util.List;


public interface RatingDao {

	int ratingInsert(Rating rating);

	int updateOpinion(Rating rating);
	
	int updateReview(Rating rating);
	
	int delete(int IdRoomReservation);

	Rating findById (int IdRoomReservation);

	List<Rating> getAllById(int IdCustomer);
	
	List<Rating> getAll();
	
	List<Rating> getAllByHighRatingStar();
	
	List<Rating> getAllByLowRatingStar();
	
	List<Rating> getAllByRatingStatus();

	int getRatingStatus(int IdRoomReservation);

	



	

}
