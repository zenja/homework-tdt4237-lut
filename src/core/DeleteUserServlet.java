package core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.appserv.jdbc.DataSource;

import core.utils.SecurityHelper;

@WebServlet("/admin/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destination  ="/admin/index.jsp";
		request.getRequestDispatcher(destination).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * only admin user can use this servlet
		 */
		HttpSession session = request.getSession();
		if (SecurityHelper.isAdminLoggedIn(session) == false) {
			String destination  ="/admin/login.jsp";
			request.getRequestDispatcher(destination).forward(request, response);
			return;
		}
		
		String uname = request.getParameter("uname");
		
		boolean isInputValid = true;
		
		/*
		 * check if params are null or empty
		 */
		if (uname == null || uname.length() == 0) {
			isInputValid = false;
		}
		
		/*
		 * check if params contains dangerous characters
		 */
		if (SecurityHelper.isContainDangerousChars(uname) == true) {
			isInputValid = false;
		}
		
		if (isInputValid == false) {
			String destination  ="/admin/invalid_input.jsp";
			request.getRequestDispatcher(destination).forward(request, response);
			return;
		}
		
		PreparedStatement stat = null;
		
		try {
			InitialContext context = new InitialContext();
			DataSource source = (DataSource) context.lookup("jdbc/lut2");
			Connection connection = source.getConnection();

			stat = 
				connection.prepareStatement("DELETE FROM users WHERE uname=?");
			
			stat.setString(1, uname);
			
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/admin/user_management_success.jsp").forward(request, response);
		
	}

}
