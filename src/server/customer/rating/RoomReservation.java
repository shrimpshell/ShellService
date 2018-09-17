package server.customer.rating;

import java.sql.Date;

public class RoomReservation {
	private int IdRoomReservation, IdCustomer, ExtrBed, IdRoomType;
	private Date ReservationDate, CheckInDate, CheckOutDate;

	
	public RoomReservation(int IdRoomReservation, Date ReservationDate, Date CheckInDate, Date CheckOutDate
			,int ExtrBed, int IdCustomer, int IdRoomType) {
		super();
		this.IdRoomReservation = IdRoomReservation;
		this.ReservationDate = ReservationDate;
		this.CheckInDate = CheckInDate;
		this.CheckOutDate = CheckOutDate;
		this.ExtrBed = ExtrBed;
		this.IdCustomer = IdCustomer;
		this.IdRoomType = IdRoomType;
	}


	public int getIdRoomReservation() {
		return IdRoomReservation;
	}


	public void setIdRoomReservation(int idRoomReservation) {
		IdRoomReservation = idRoomReservation;
	}


	public int getIdCustomer() {
		return IdCustomer;
	}


	public void setIdCustomer(int idCustomer) {
		IdCustomer = idCustomer;
	}


	public int getExtrBed() {
		return ExtrBed;
	}


	public void setExtrBed(int extrBed) {
		ExtrBed = extrBed;
	}


	public int getIdRoomType() {
		return IdRoomType;
	}


	public void setIdRoomType(int idRoomType) {
		IdRoomType = idRoomType;
	}


	public Date getReservationDate() {
		return ReservationDate;
	}


	public void setReservationDate(Date reservationDate) {
		ReservationDate = reservationDate;
	}


	public Date getCheckInDate() {
		return CheckInDate;
	}


	public void setCheckInDate(Date checkInDate) {
		CheckInDate = checkInDate;
	}


	public Date getCheckOutDate() {
		return CheckOutDate;
	}


	public void setCheckOutDate(Date checkOuntDate) {
		CheckOutDate = checkOuntDate;
	}


}

