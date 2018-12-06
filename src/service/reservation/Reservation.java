package service.reservation;


public class Reservation {
	public String roomTypeName, reservationDate, checkInDate, checkOutDate;
	public int roomTypeId, quantity, extraBed, price, eventId, roomGroup, customerId;
	
	public Reservation(String roomTypeName, String reservationDate, String checkInDate, String checkOutDate,
			int roomTypeId, int quantity, int extraBed, int price, int eventId, int roomGroup, int customerId) {
		super();
		this.roomTypeName = roomTypeName;
		this.reservationDate = reservationDate;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomTypeId = roomTypeId;
		this.quantity = quantity;
		this.extraBed = extraBed;
		this.price = price;
		this.eventId = eventId;
		this.roomGroup = roomGroup;
		this.customerId = customerId;
	}

	public Reservation(String reservationDate, String checkInDate, String checkOutDate, int extraBed, int quantity,
			int customerId, int roomTypeId, int eventId, int roomGroup, int price) {
		super();
		this.reservationDate = reservationDate;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.extraBed = extraBed;
		this.quantity = quantity;
		this.customerId = customerId;
		this.roomTypeId = roomTypeId;
		this.eventId = eventId;
		this.roomGroup = roomGroup;
		this.price = price;
	}

	public Reservation(int roomTypeId, int quantity) {
		super();
		this.roomTypeId = roomTypeId;
		this.quantity = quantity;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getExtraBed() {
		return extraBed;
	}

	public void setExtraBed(int extraBed) {
		this.extraBed = extraBed;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getRoomGroup() {
		return roomGroup;
	}

	public void setRoomGroup(int roomGroup) {
		this.roomGroup = roomGroup;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
}
