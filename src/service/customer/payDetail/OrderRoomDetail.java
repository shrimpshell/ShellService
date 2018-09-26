package service.customer.payDetail;

public class OrderRoomDetail {
	private int idRoomReservation;
	private String checkInDate, checkOuntDate, roomNumber, price;
	public OrderRoomDetail(int idRoomReservation, String checkInDate, String checkOuntDate,
			String roomNumber, String price) {
		super();
		this.idRoomReservation = idRoomReservation;
		this.price = price;
		this.checkInDate = checkInDate;
		this.checkOuntDate = checkOuntDate;
		this.roomNumber = roomNumber;
	}
	public int getIdRoomReservation() {
		return idRoomReservation;
	}
	public String getPrice() {
		return price;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public String getCheckOuntDate() {
		return checkOuntDate;
	}
	public String getRoomNumber() {
		return roomNumber;
	}	
}
