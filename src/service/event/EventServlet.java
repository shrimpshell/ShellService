package service.event;

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

import service.room.Room;

@SuppressWarnings("serial")
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	EventDao eventDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (eventDao == null) {
			eventDao = new EventDaoMySqlImpl();
		}
		List<Events> events = eventDao.getAll();
		writeText(response, new Gson().toJson(events));
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
		if (eventDao == null) {
			eventDao = new EventDaoMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Events> events = eventDao.getAll();
			writeText(response, gson.toJson(events));
		} else if (action.equals("getFive")) {
			List<Events> events = eventDao.getFive();
			writeText(response, gson.toJson(events));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("imageId").getAsInt();
			System.out.println(id);
			byte[] image = eventDao.getImage(id);
			if (image != null) {
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		} else if (action.equals("eventInsert") || action.equals("eventUpdate")) {
			String eventJson = jsonObject.get("event").getAsString();
			Events event = gson.fromJson(eventJson, Events.class);
			
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = null;
			if (imageBase64.length() > 0) image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("eventInsert")) {
				count = eventDao.insert(event, image);
				writeText(response, String.valueOf(count));
			} else if (action.equals("eventUpdate")) {
				count = eventDao.update(event, image);
				writeText(response, String.valueOf(count));
			} else {
				writeText(response, "");
			}
		} else if (action.equals("eventRemove")) {
			String eventJson = jsonObject.get("event").getAsString();
			Events event = gson.fromJson(eventJson, Events.class);
			int count = eventDao.delete(event.getEventId());
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
