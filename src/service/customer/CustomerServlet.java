package service.customer;

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
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	CustomerDao customerDao = null;
   
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
		if (customerDao == null) {
			customerDao = new CustomerDaoMySqlImpl();
		}
		String action = jsonObject.get("action").getAsString();
		//user登入方法
		if(action.equals("userValid")) {
			String email = jsonObject.get("email").getAsString();
			String password = jsonObject.get("password").getAsString();
			writeText(response, String.valueOf(customerDao.userValid(email, password)));
		//檢查申請帳號是否重複
		}else if(action.equals("userExist")) {
			String email = jsonObject.get("email").getAsString();
			writeText(response, String.valueOf(customerDao.userExist(email)));
		//會員註冊
		}else if(action.equals("customerInsert")) {
			String customerJson = jsonObject.get("customer").getAsString();
			Customer customer = gson.fromJson(customerJson, Customer.class);
			writeText(response, String.valueOf(customerDao.customerInsert(customer)));
		//會員資料更新
		}else if(action.equals("update")) {
			Customer customer = gson.fromJson(jsonObject.get("customer")
					.getAsString(), Customer.class);
			writeText(response, String.valueOf(customerDao.update(customer)));
		//會員圖片更新
		}else if(action.equals("updateImage")) {
			String IdCustomer = jsonObject.get("IdCustomer").getAsString();
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = null;
			if (imageBase64.length() > 0) image = Base64.getMimeDecoder().decode(imageBase64);
			int count = 0;
			count = customerDao.updateImage(Integer.valueOf(IdCustomer), image);
			writeText(response, String.valueOf(count));
		//用id尋找會員資料
		}else if(action.equals("findById")) {
			String IdCustomer = jsonObject.get("IdCustomer").getAsString();
			Customer customer = customerDao.findById(Integer.valueOf(IdCustomer));
			writeText(response, gson.toJson(customer));
		//刪除會員資料
		}else if(action.equals("delete")) {
			String spotJson = jsonObject.get("customer").getAsString();
			Customer customer = gson.fromJson(spotJson, Customer.class);
			int count = customerDao.delete(customer.getIdCustomer());
			writeText(response, String.valueOf(count));
		//會員頁面顯示入住資訊
		} else if(action.equals("getRoomReservationStatus")) {
			String IdCustomer = jsonObject.get("IdCustomer").getAsString();
			Customer customer = customerDao.getRoomReservationStatus(Integer.valueOf(IdCustomer));
			writeText(response, gson.toJson(customer));
		//取得所有會員資訊
		}else if(action.equals("getAllById")) {
			List<Customer> customers = customerDao.getAll();
			writeText(response, gson.toJson(customers));
		}else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int IdCustomer = jsonObject.get("IdCustomer").getAsInt();
			byte[] image = customerDao.getImage(IdCustomer);
			if (image != null) {
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (customerDao == null) {
			customerDao = new CustomerDaoMySqlImpl();
		}
		List<Customer> customers = customerDao.getAll();
		writeText(response, new Gson().toJson(customers));
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}

	

}
