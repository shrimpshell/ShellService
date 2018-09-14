package service.employee;

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


@SuppressWarnings("serial")
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	EmployeeDao employeeDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (employeeDao == null) {
			employeeDao = new EmployeeDaoMySqlImpl();
		}
		List<Employees> employees = employeeDao.getAll();
		writeText(response, new Gson().toJson(employees));
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
		if (employeeDao == null) {
			employeeDao = new EmployeeDaoMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Employees> events = employeeDao.getAll();
			writeText(response, gson.toJson(events));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("imageId").getAsInt();
			byte[] image = employeeDao.getImage(id);
			if (image != null) {
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		} else if (action.equals("employeeInsert") || action.equals("employeeUpdate")) {
			String employeeJson = jsonObject.get("employee").getAsString();
			Employees employee = gson.fromJson(employeeJson, Employees.class);
			
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = null;
			if (imageBase64.length() > 0) image = Base64.getMimeDecoder().decode(imageBase64);
			
			int count = 0;
			if (action.equals("employeeInsert")) {
				count = employeeDao.insert(employee, image);
				writeText(response, String.valueOf(count));
			} else if (action.equals("employeeUpdate")) {
				count = employeeDao.update(employee, image);
				writeText(response, String.valueOf(count));
			} else {
				writeText(response, "");
			}
		} else if (action.equals("employeeRemove")) {
			String employeeJson = jsonObject.get("employee").getAsString();
			Employees employee = gson.fromJson(employeeJson, Employees.class);
			System.out.println(employee.getId());
			int count = employeeDao.delete(employee.getId());
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