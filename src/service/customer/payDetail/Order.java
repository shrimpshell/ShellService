package service.customer.payDetail;

import java.util.List;

public class Order {
	private int IdRoomReservation;
	private String CheckInDate;
	private String CheckOuntDate;
	private List<OrderDetail> orderDetailList;

	public Order() {
		super();
	}

	public Order(int idRoomReservation, String checkInDate, String checkOuntDate, List<OrderDetail> orderDetailList) {
		super();
		IdRoomReservation = idRoomReservation;
		CheckInDate = checkInDate;
		CheckOuntDate = checkOuntDate;
		this.orderDetailList = orderDetailList;
	}

	public int getIdRoomReservation() {
		return IdRoomReservation;
	}

	public void setIdRoomReservation(int idRoomReservation) {
		IdRoomReservation = idRoomReservation;
	}

	public String getCheckInDate() {
		return CheckInDate;
	}

	public void setCheckInDate(String checkInDate) {
		CheckInDate = checkInDate;
	}

	public String getCheckOuntDate() {
		return CheckOuntDate;
	}

	public void setCheckOuntDate(String checkOuntDate) {
		CheckOuntDate = checkOuntDate;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	

}
