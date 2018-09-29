package service.instant;

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
@WebServlet("/InstantServlet")
public class InstantServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	InstantDao instantDao = null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (instantDao == null) {
			instantDao = new InstantDaoMySqlImpl();
		}
		
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
		
		if (instantDao == null) {
			instantDao = new InstantDaoMySqlImpl();
		}
		String action = jsonObject.get("action").getAsString();		
		if (action.equals("insertInstant")) {
			int idInstantDetail = 0;		
			String instantJson = jsonObject.get("instant").getAsString();		
			Instant instant = gson.fromJson(instantJson, Instant.class);						
			idInstantDetail = instantDao.insertInstant(instant);		
			writeText(response, gson.toJson(idInstantDetail));
		} else if (action.equals("updateStatus")) {
			int count = 0;
			int idInstantDetail = jsonObject.get("idInstantDetail").getAsInt();
			int status = jsonObject.get("status").getAsInt();
			count = instantDao.updateStatus(idInstantDetail, status);
			writeText(response, String.valueOf(count));
		} else if (action.equals("getEmployeeStatus")) {
			int idInstantService = jsonObject.get("idInstantService").getAsInt();
			List<Instant> instants = instantDao.getEmployeeStatus(idInstantService);
			writeText(response, gson.toJson(instants));
		} else if (action.equals("getCustomerStatus")) {
			String roomNumber = jsonObject.get("roomNumber").getAsString();
			List<Instant> instants = instantDao.getCustomerStatus(roomNumber);
			writeText(response, gson.toJson(instants));
		}
		
	}


	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
		
	}

}
