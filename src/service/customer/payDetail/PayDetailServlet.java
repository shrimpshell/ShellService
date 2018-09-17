package service.customer.payDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/PayDetailServlet")
public class PayDetailServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	PayDetailDao payDetailDao = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (payDetailDao == null) {
			payDetailDao = new PayDetailDaoMySqlImpl();
		}
		List<Order> payDetails = payDetailDao.getPayDetailById("1");
		writeText(response, new Gson().toJson(payDetails));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (payDetailDao == null) {
			payDetailDao = new PayDetailDaoMySqlImpl();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("findPayDetailById")) {
			String idCustomer = jsonObject.get("idCustomer").getAsString();
			List<Order> payDetails = payDetailDao.getPayDetailById(idCustomer);
			writeText(response, gson.toJson(payDetails));
		}else if (action.equals("updateRoomReservationStatusById")) {
			String roomReservationStatus = jsonObject.get("roomReservationStatus").getAsString();
			String idRoomReservation = jsonObject.get("idRoomReservation").getAsString();
			int count = 0;
			count = payDetailDao.updateRoomReservationStatusById(roomReservationStatus, idRoomReservation);
			
			writeText(response, String.valueOf(count));
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("outText: " + outText);
	}

}