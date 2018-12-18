package service.roomtype;

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
import service.reservation.Reservation;

@SuppressWarnings("serial")
@WebServlet("/RoomTypeServlet")
public class RoomTypeServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	RoomTypeDao roomTypeDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (roomTypeDao == null) {
			roomTypeDao = new RoomTypeDaoMySqlImpl();
		}
		String action = request.getParameter("action");
		String imageId = request.getParameter("imageId");
		if (action.equals("getAll")) {
			List<RoomType> rooms = roomTypeDao.getAll();
			writeText(response, new Gson().toJson(rooms));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = Integer.parseInt(imageId);
			byte[] image = roomTypeDao.getImage(id);
			if (image != null) {
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			try {
				os.write(image);
			} catch (NullPointerException e) {
				writeText(response, "no image");
			}
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
		if (roomTypeDao == null) {
			roomTypeDao = new RoomTypeDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<RoomType> rooms = roomTypeDao.getAll();
			writeText(response, gson.toJson(rooms));
		} else if (action.equals("getRoomType")) {
			String checkInDate = jsonObject.get("checkInDate").getAsString();
			String checkOutDate = jsonObject.get("checkOutDate").getAsString();
			List<RoomType> rooms = roomTypeDao.getRoomType(checkInDate, checkOutDate);
			writeText(response, gson.toJson(rooms));
		}else if (action.equals("getFive")) {
			List<RoomType> rooms = roomTypeDao.getFive();
			writeText(response, gson.toJson(rooms));
		} else if (action.equals("getReservation")) {
			String checkInDate = jsonObject.get("checkInDate").getAsString();
			String checkOutDate = jsonObject.get("checkOutDate").getAsString();
			List<RoomType> rooms = roomTypeDao.getReservation(checkInDate, checkOutDate);
			writeText(response, gson.toJson(rooms));
		} else if (action.equals("findByRoomId")) {
			String checkInDate = jsonObject.get("checkInDate").getAsString();
			String checkOutDate = jsonObject.get("checkOutDate").getAsString();
			int roomTypeId = jsonObject.get("roomTypeId").getAsInt();
			List<RoomType> reservations = roomTypeDao.findByRoomId(checkInDate, checkOutDate, roomTypeId);
			writeText(response, gson.toJson(reservations));
		} else if (action.equals("roomInsert") || action.equals("roomUpdate")) {
			String spotJson = jsonObject.get("room").getAsString();
			RoomType room = gson.fromJson(spotJson, RoomType.class);
			
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = null;
			if (imageBase64.length() > 0) image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("roomInsert")) {
				RoomType rooms = roomTypeDao.insert(room, image);
				count = roomTypeDao.insertRoomNumber(rooms.getId(), rooms.getRoomNumber());
				writeText(response, String.valueOf(count));
			} else if (action.equals("roomUpdate")) {
				count = roomTypeDao.update(room, image);
				writeText(response, String.valueOf(count));
			} else {
				writeText(response, "");
			}
			
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("imageId").getAsInt();
			byte[] image = roomTypeDao.getImage(id);
			if (image != null) {
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			try {
				os.write(image);
			} catch (NullPointerException e) {
				writeText(response, "no image");
			}
		} else if (action.equals("roomRemove")) {
			String roomJson = jsonObject.get("room").getAsString();
			RoomType room = gson.fromJson(roomJson, RoomType.class);
			int count = roomTypeDao.delete(room.getId());
			writeText(response, String.valueOf(count));
		}

		
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
