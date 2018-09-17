package server.customer.rating;

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


@SuppressWarnings("serial")
@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	private static int IdCustomer = 0;
	RatingDao ratingDao = null;
	
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
		if (ratingDao == null) {
			ratingDao = new RatingDaoMySqlImpl();
		}
		String action = jsonObject.get("action").getAsString();
		
		//新增評論
		if(action.equals("ratingInsert")) {
			String ratingJson = jsonObject.get("rating").getAsString();
			Rating rating = gson.fromJson(ratingJson, Rating.class);
			writeText(response, String.valueOf(ratingDao.ratingInsert(rating)));
		//Customer修改評論
		}else if(action.equals("updateOpinion")) {
			Rating rating = gson.fromJson(jsonObject.get("rating")
					.getAsString(), Rating.class);
			writeText(response, String.valueOf(ratingDao.updateOpinion(rating)));
		//商家端修改回覆
		}else if(action.equals("updateReview")) {
			Rating rating = gson.fromJson(jsonObject.get("rating")
					.getAsString(), Rating.class);
			writeText(response, String.valueOf(ratingDao.updateReview(rating)));
		//尋找Customer評論
//		}else if(action.equals("findById")) {
//			String IdCustomer = jsonObject.get("IdCustomer").getAsString();
//			Customer customer = ratingDao.findById(Integer.valueOf(IdCustomer));
//			writeText(response, gson.toJson(customer));
		}else if(action.equals("delete")) {
			String ratingJson = jsonObject.get("rating").getAsString();
			Rating rating = gson.fromJson(ratingJson, Rating.class);
			int count = ratingDao.delete(rating.getIdRating());
			writeText(response, String.valueOf(count));
		}else if(action.equals("getAll")) {
			int IdCustomer = jsonObject.get("IdCustomer").getAsInt();
			List<Rating> ratings = ratingDao.getAll(IdCustomer);
			writeText(response, gson.toJson(ratings));
		}
	}
			
		private void writeText(HttpServletResponse response, String outText) throws IOException {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.print(outText);
			System.out.println("output: " + outText);
		}
	

}
