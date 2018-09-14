package service.customer.payDetail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet("/PayDetailServlet")
public class PayDetailServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	PayDetailDaoMySqlImpl payDetailDaoMySqlImpl = null;
	
	// the request method for Android is set to be Post
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// just return order information (default page's request method is Get)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (payDetailDaoMySqlImpl == null) {
			payDetailDaoMySqlImpl = new PayDetailDaoMySqlImpl();
		}
		List<Order> payDetails = payDetailDaoMySqlImpl.getPayDetailById("1");
		writeText(response, new Gson().toJson(payDetails));
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("outText: " + outText);
	}
	
}