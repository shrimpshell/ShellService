package service.room;

import java.sql.Blob;

public class Room {
	private int id, price, roomQuantity, adultQuantity, childQuantity;
	private String name, detail;
	private Blob image;
	public Room(String name, String detail, int adultQuantity, int childQuantity, int roomQuantity, int price, Blob image) {
		super();
		this.price = price;
		this.roomQuantity = roomQuantity;
		this.adultQuantity = adultQuantity;
		this.childQuantity = childQuantity;
		this.name = name;
		this.detail = detail;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRoomQuantity() {
		return roomQuantity;
	}
	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}
	public int getAdultQuantity() {
		return adultQuantity;
	}
	public void setAdultQuantity(int adultQuantity) {
		this.adultQuantity = adultQuantity;
	}
	public int getChildQuantity() {
		return childQuantity;
	}
	public void setChildQuantity(int childQuantity) {
		this.childQuantity = childQuantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
