package service.customer.payDetail;

public class OrderDetail {
	private String roomNumber;// 房號Ø
	private String price;// 房間價格
	private String diningTypeName;// 餐點名稱
	private String quantity;// 餐點數量
	private String dtPrice;// 餐點價格
	private String discount;// 訂房時有在優惠活動範圍內的折扣,也可能沒有

	public OrderDetail() {
		super();
	}

	public OrderDetail(String roomNumber, String price, String diningTypeName, String quantity, String dtPrice, String discount) {
		this.roomNumber = roomNumber;
		this.price = price;
		this.diningTypeName = diningTypeName;
		this.quantity = quantity;
		this.dtPrice = dtPrice;
		this.discount = discount == null ? "1.0" : discount;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}

	public String getPrice() {
		return price;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

}
