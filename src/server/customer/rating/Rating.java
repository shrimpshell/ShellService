package server.customer.rating;

public class Rating {
	
	int IdRating, IdRoomReservation, IdCustomer;
	Float ratingStar;
	String opinion, review, time, Name;
//	private List<RatingDetail> ratingDetailList;
	
	public Rating() {
		super();
	}
	
	public Rating(int IdRoomReservation, int IdCustomer) {
		super();
		this.IdRoomReservation = IdRoomReservation;
		this.IdCustomer = IdCustomer;
	}
	
	public Rating(int IdRating, Float ratingStar, String time, String opinion, String review, 
		int IdRoomReservation) {
		this.IdRating = IdRating;
		this.time = time;
		this.ratingStar = ratingStar;
		this.opinion = opinion;
		this.review = review;
		this.IdRoomReservation = IdRoomReservation;
	}
	
	public Rating (Float ratingStar, String time, String opinion, String review, int idRoomReservation
			, int IdCustomer) {
		this.ratingStar = ratingStar;
		this.time = time;
		this.opinion = opinion;
		this.review = review;
		this.IdRoomReservation = idRoomReservation;
		this.IdCustomer = IdCustomer;
	}
	
	public Rating (Float ratingStar, String time, String opinion, String review, int idRoomReservation
			, String Name) {
		this.ratingStar = ratingStar;
		this.time = time;
		this.opinion = opinion;
		this.review = review;
		this.IdRoomReservation = idRoomReservation;
		this.Name = Name;
	}
	
	public Rating(int IdRoomReservation, Float ratingStar, String opinion) {
		this.IdRoomReservation = IdRoomReservation;
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

	public int getIdCustomer() {
		return IdCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		IdCustomer = idCustomer;
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
		this.Name = name;
	}
	
	

//	public List<RatingDetail> getRatingDetailList() {
//		return ratingDetailList;
//	}
//
//	public void setRatingDetailList(List<RatingDetail> ratingDetailList) {
//		this.ratingDetailList = ratingDetailList;
//	}
	

}
