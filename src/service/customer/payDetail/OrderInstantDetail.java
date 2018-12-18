package service.customer.payDetail;

public class OrderInstantDetail {
	private String instantTypeName, quantity, instantPrice, roomGroup;

	public OrderInstantDetail(String instantTypeName, String quantity, String instantPrice, String roomGroup) {
		this.instantTypeName = instantTypeName;
		this.quantity = quantity;
		this.instantPrice = instantPrice;
		this.roomGroup = roomGroup;
	}

	public String getDiningTypeName() {
		return instantTypeName;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getDtPrice() {
		return instantPrice;
	}

	public String getRoomGroup() {
		return roomGroup;
	}
	
}
