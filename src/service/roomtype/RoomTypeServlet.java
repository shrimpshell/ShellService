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
		List<RoomType> rooms = roomTypeDao.getAll();
		writeText(response, new Gson().toJson(rooms));
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
			String checkInDate = jsonObject.get("checkInDate").getAsString();
			String checkOutDate = jsonObject.get("checkOutDate").getAsString();
			List<RoomType> rooms = roomTypeDao.getAll(checkInDate, checkOutDate);
			writeText(response, gson.toJson(rooms));
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
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
