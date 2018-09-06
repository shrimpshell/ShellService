package service.customer;

import com.mysql.cj.jdbc.Blob;

public class Customer {
	private String CustomerID, Name, Email, Password, Gender, Phone, Address;
	private int discount;
	private Blob CustomerPic;
	public Customer(String customerID, String name, String email, String password, String gender, String phone,
			String address, int discount, Blob customerPic) {
		super();
		CustomerID = customerID;
		Name = name;
		Email = email;
		Password = password;
		Gender = gender;
		Phone = phone;
		Address = address;
		this.discount = discount;
		CustomerPic = customerPic;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public Blob getCustomerPic() {
		return CustomerPic;
	}
	public void setCustomerPic(Blob customerPic) {
		CustomerPic = customerPic;
	}
	
	
}
