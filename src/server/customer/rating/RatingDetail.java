package server.customer.rating;

import java.util.List;

public class RatingDetail {
	private int IdRoomReservation;
	private List<RatingDetail> ratingDetailList;
	
	public RatingDetail() {
		super();
	}
	
	public RatingDetail(int IdRoomReservation, List<RatingDetail> ratingDetailList) {
		super();
		this.IdRoomReservation = IdRoomReservation;
		this.ratingDetailList = ratingDetailList;
	}

	public int getIdRoomReservation() {
		return IdRoomReservation;
	}

	public void setIdRoomReservation(int idRoomReservation) {
		IdRoomReservation = idRoomReservation;
	}

	public List<RatingDetail> getRatingDetailList() {
		return ratingDetailList;
	}

	public void setRatingDetailList(List<RatingDetail> ratingDetailList) {
		this.ratingDetailList = ratingDetailList;
	}
	
	
}
