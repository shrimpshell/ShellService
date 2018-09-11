package service.room;

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
@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	RoomDao roomDao = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (roomDao == null) {
			roomDao = new RoomDaoMySqlImpl();
		}
		List<Room> spots = roomDao.getAll();
		writeText(response, new Gson().toJson(spots));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		if (roomDao == null) {
			roomDao = new RoomDaoMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			List<Room> rooms = roomDao.getAll();
			writeText(response, gson.toJson(rooms));
		} else if (action.equals("roomInsert") || action.equals("roomUpdate")) {
			String spotJson = jsonObject.get("room").getAsString();
			Room room = gson.fromJson(spotJson, Room.class);
			
//			String imageBase64 = jsonObject.get("imageBase64").getAsString();
//			byte[] image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("roomInsert")) {
				count = roomDao.insert(room, null);
			} else if (action.equals("roomUpdate")) {
				count = roomDao.update(room, null);
			} else if (action.equals("findById")) {
				int id = jsonObject.get("id").getAsInt();
				Room spot = roomDao.findById(id);
				writeText(response, gson.toJson(spot));
			} else {
				writeText(response, "");
			}
			
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
