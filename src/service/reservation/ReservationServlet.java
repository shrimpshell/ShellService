package service.reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ReservationDao reservationDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (reservationDao == null) {
			reservationDao = new ReservationDaoMySqlImpl();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (reservationDao == null) {
			reservationDao = new ReservationDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("insertReservation")) {
			String reservationsJson = jsonObject.get("reservation").getAsString();		
			Reservation reservation = gson.fromJson(reservationsJson, Reservation.class);						
			Reservation roomReservation = reservationDao.insertReservation(reservation);
			for(int i = 1; i <= reservation.quantity; i++) {
				String roomNumber = reservationDao.findRoomNumber(reservation.checkInDate, reservation.checkOutDate, reservation.roomTypeId);
				int roomStatusId = reservationDao.insertRoomStatus(roomNumber, roomReservation.roomReservationId);
			}
			writeText(response, (String.valueOf(roomReservation.roomReservationId)));
		}
	}
	
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
