package service.customer.payDetail;

import java.util.List;

public class Order {
	private int idRoomReservation;
	private String checkInDate;
	private String checkOuntDate;
	private String roomReservationStatus;
	private List<OrderDetail> orderDetailList;

	public Order() {
		super();
	}

	public Order(int idRoomReservation, String checkInDate, String checkOuntDate, String roomReservationStatus,
			List<OrderDetail> orderDetailList) {
		super();
		this.idRoomReservation = idRoomReservation;
		this.checkInDate = checkInDate;
		this.checkOuntDate = checkOuntDate;
		this.roomReservationStatus = roomReservationStatus;
		this.orderDetailList = orderDetailList;
	}

	public int getIdRoomReservation() {
		return idRoomReservation;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public String getCheckOuntDate() {
		return checkOuntDate;
	}

	public String getRoomReservationStatus() {
		return roomReservationStatus;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

}
