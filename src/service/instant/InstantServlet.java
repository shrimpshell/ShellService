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

import service.customer.Customer;
import service.employee.Employees;





@SuppressWarnings("serial")
@WebServlet("/InstantServlet")
public class InstantServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	InstantDao instantDao = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (instantDao == null) {
			instantDao = new InstantDaoMySqlImpl();
		}
		List<Instant> instants = instantDao.getAll();
		writeText(response, new Gson().toJson(instants));
	}
	
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		if (action.equals("getAll")) {
			List<Instant> events = instantDao.getAll();
			writeText(response, gson.toJson(events));
		} else if (action.equals("instantInsert")) {
			String instantJson = jsonObject.get("instant").getAsString();
			Instant instant = gson.fromJson(instantJson, Instant.class);
			writeText(response, String.valueOf(instantDao.insert(instant)));
		} else if (action.equals("instantUpdate")) {
			Instant instant = gson.fromJson(jsonObject.get("instant").getAsString(), Instant.class);
			writeText(response, String.valueOf(instantDao.update(instant)));
			
		}
	}
	
	private void writeText(HttpServletResponse response, String outText)  throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
		
	}
	
	

}
