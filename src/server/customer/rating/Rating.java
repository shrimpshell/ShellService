package server.customer.rating;

public class Rating {
	
	int IdRating, IdRoomReservation, IdCustomer, ratingStatus;
	Float ratingStar;
	String opinion, review, time, Name;
	public Rating(int idRating, int idRoomReservation, int idCustomer, Float ratingStar, String opinion, String review,
			String time, String name, int ratingStatus) {
		super();
		IdRating = idRating;
		IdRoomReservation = idRoomReservation;
		IdCustomer = idCustomer;
		this.ratingStar = ratingStar;
		this.opinion = opinion;
		this.review = review;
		this.time = time;
		Name = name;
		this.ratingStatus = ratingStatus;
	}
	public Rating(int idRoomReservation, int idCustomer) {
		super();
		IdRoomReservation = idRoomReservation;
		IdCustomer = idCustomer;
	}
	public Rating(Float ratingStar, String time, String opinion, String review, int ratingStatus, int idRoomReservation) {
		super();
		
		this.ratingStar = ratingStar;
		this.time = time;
		this.opinion = opinion;
		this.review = review;
		this.ratingStatus = ratingStatus;
		IdRoomReservation = idRoomReservation;
	
	}
	public Rating(int idRating, Float ratingStar, String time, String opinion, String review, int idRoomReservation,
			int idCustomer) {
		super();
		IdRating = idRating;
		this.ratingStar = ratingStar;
		this.time = time;
		this.opinion = opinion;
		this.review = review;
		IdRoomReservation = idRoomReservation;
		IdCustomer = idCustomer;
	}
	
	public Rating(Float ratingStar, String time, String opinion, String review, int ratingStatus, int idRoomReservation, String name) {
		super();
		this.ratingStar = ratingStar;
		this.time = time;
		this.opinion = opinion;
		this.review = review;
		this.ratingStatus = ratingStatus;
		IdRoomReservation = idRoomReservation;
		Name = name;
	}
	public Rating(int idRoomReservation, Float ratingStar, String opinion) {
		super();
		IdRoomReservation = idRoomReservation;
		this.ratingStar = ratingStar;
		this.opinion = opinion;
	}
	public int getIdRating() {
		return IdRating;
	}
	public void setIdRating(int idRating) {
		IdRating = idRating;
	}
	public int getIdRoomReservation() {
		return IdRoomReservation;
	}
	public void setIdRoomReservation(int idRoomReservation) {
		IdRoomReservation = idRoomReservation;
	}
	public int getIdCustomer() {
		return IdCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		IdCustomer = idCustomer;
	}
	public Float getRatingStar() {
		return ratingStar;
	}
	public void setRatingStar(Float ratingStar) {
		this.ratingStar = ratingStar;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getRatingStatus() {
		return ratingStatus;
	}
	public void setRatingStatus(int ratingStatus) {
		this.ratingStatus = ratingStatus;
	}
	
	
	

	
	
	

}
