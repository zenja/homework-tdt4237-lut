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

@WebServlet("/admin/modifyPassword")
public class ModifyPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModifyPasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String newPassword = request.getParameter("new_password");
		
		boolean isInputValid = true;
		
		/*
		 * check if params are null or empty
		 */
		if (uname == null || newPassword == null) {
			isInputValid = false;
		}
		if (uname.length() == 0 || newPassword.length() == 0) {
			isInputValid = false;
		}
		
		/*
		 * check if params contains dangerous characters
		 */
		if (SecurityHelper.isContainDangerousChars(uname) == true || 
				SecurityHelper.isContainDangerousChars(newPassword) == true ) {
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
				connection.prepareStatement("UPDATE users SET pw=? where uname=?");
			
			stat.setString(1, newPassword);
			stat.setString(2, uname);
			
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/admin/user_management_success.jsp").forward(request, response);
		
	}

}
