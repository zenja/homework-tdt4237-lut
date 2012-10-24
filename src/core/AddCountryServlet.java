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

import com.sun.appserv.jdbc.DataSource;

import core.utils.SecurityHelper;

@WebServlet("/admin/addCountry")
public class AddCountryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCountryServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destination  ="/admin/index.jsp";
		request.getRequestDispatcher(destination).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String shortName = request.getParameter("short_name");
		String fullName = request.getParameter("full_name");
		
		boolean isInputValid = true;
		
		/*
		 * check if params are null or empty
		 */
		if (shortName == null || fullName == null) {
			isInputValid = false;
		}
		if (shortName.length() == 0 || fullName.length() == 0 ) {
			isInputValid = false;
		}
		
		/*
		 * check if params contains dangerous characters
		 */
		if (SecurityHelper.isContainDangerousChars(shortName) == true || 
				SecurityHelper.isContainDangerousChars(fullName) == true ) {
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
				connection.prepareStatement("INSERT INTO country(short_name, full_name) VALUES (?, ?)");
			
			stat.setString(1, shortName);
			stat.setString(2, fullName);
			
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/admin/add_country_success.jsp").forward(request, response);
	}

}
