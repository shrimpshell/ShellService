package service.reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.ImageUtil;
import service.employee.Employees;
import service.instant.Instant;
import service.roomtype.RoomType;

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
		List<Reservation> reservations = reservationDao.getAll();
		writeText(response, new Gson().toJson(reservations));
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
			int count = 0;
			String reservationsJson = jsonObject.get("reservation").getAsString();		
			Reservation reservation = gson.fromJson(reservationsJson, Reservation.class);						
			count = reservationDao.insertReservation(reservation);		
			writeText(response, (String.valueOf(count)));
		}
	}


	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
