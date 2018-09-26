package service.customer.payDetail;

public class OrderInstantDetail {
	private String diningTypeName, quantity, dtPrice, roomReservationStatus;

	public OrderInstantDetail(String diningTypeName, String quantity, String dtPrice, String roomReservationStatus) {
		this.diningTypeName = diningTypeName;
		this.quantity = quantity;
		this.dtPrice = dtPrice;
		this.roomReservationStatus = roomReservationStatus;
	}

	public String getDiningTypeName() {
		return diningTypeName;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getDtPrice() {
		return dtPrice;
	}

	public String getRoomReservationStatus() {
		return roomReservationStatus;
	}
	
}
