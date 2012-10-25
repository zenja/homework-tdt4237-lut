package core;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/UserRegistrationServlet")
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL, value = "POST"),
		@HttpMethodConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL, value = "GET") })
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegistrationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username_j");
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(username);
			if (!matcher.matches()) {
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
				return;
			}
			UserSession user = new UserSession();
			user.setUsername(username);
			user.setPassword(request.getParameter("password_j"));
			user.setQuestion(request.getParameter("question"));
			user.setAnswer(request.getParameter("answer"));
			user = UserConnection.create(user);
			// System.out.println(request.getParameter("username_j"));
			// System.out.println(request.getParameter("password_j"));
			// System.out.println(user.isLoggedIn());

			if (user.isLoggedIn()) {
				HttpSession session = request.getSession(true);
				session.setMaxInactiveInterval(60 * 60); // one hour
				session.setAttribute("currentUser", user);
				response.sendRedirect("index.jsp");
			} else {

				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
