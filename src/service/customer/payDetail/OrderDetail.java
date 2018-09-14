package service.customer.payDetail;

public class OrderDetail {
	private String RoomNumber;
	private String RoomLevel;
	private String RoomType;
	private String Price;
	private String DiningTypeName;
	private String Quantity;
	private String dtPrice;
	private String Discount;

	public OrderDetail() {
		super();
	}

	public OrderDetail(String roomNumber, String roomLevel, String roomType, String price, String diningTypeName,
			String quantity, String dtPrice, String discount) {
		super();
		RoomNumber = roomNumber;
		RoomLevel = roomLevel;
		RoomType = roomType;
		Price = price;
		DiningTypeName = diningTypeName;
		Quantity = quantity;
		this.dtPrice = dtPrice;
		Discount = discount;
	}

	public String getRoomNumber() {
		return RoomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		RoomNumber = roomNumber;
	}

	public String getRoomLevel() {
		return RoomLevel;
	}

	public void setRoomLevel(String roomLevel) {
		RoomLevel = roomLevel;
	}

	public String getRoomType() {
		return RoomType;
	}

	public void setRoomType(String roomType) {
		RoomType = roomType;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getDiningTypeName() {
		return DiningTypeName;
	}

	public void setDiningTypeName(String diningTypeName) {
		DiningTypeName = diningTypeName;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getDtPrice() {
		return dtPrice;
	}

	public void setDtPrice(String dtPrice) {
		this.dtPrice = dtPrice;
	}

	public String getDiscount() {
		return Discount;
	}

	public void setDiscount(String discount) {
		Discount = discount;
	}

	
}
