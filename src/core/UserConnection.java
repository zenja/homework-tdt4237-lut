package core;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;

import com.sun.appserv.jdbc.DataSource;

public class UserConnection {

	static Connection connection = null;
	static ResultSet resultSet = null;
	
	static MessageDigest md = null;

	public static UserSession login(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();
		String password = session.getPassword();
		if (username == null || password == null) {
			session.setLoggedIn(false);
			return session;
		}
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("iso-8859-1"), 0, password.length());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] md5hash = new byte[32];
		md5hash = md.digest();
		password = convertToHex(md5hash);
		
		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=? AND pw=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			resultSet = pst.executeQuery();
			// check result
			if(resultSet.next()){
				session.setAdminRole(resultSet.getBoolean("adminrole"));
				session.setLoggedIn(true);
			} else {
				session.setLoggedIn(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				pst.close();
				connection.close();
			} catch (Exception e) {				
			}
		}
		return session;
	}

	public static UserSession getQuestion(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			resultSet = pst.executeQuery();
			String question;
			// check result
			if(resultSet.next()){
				question = resultSet.getString("question");
			} else {
				question = "";
			}
			session.setQuestion(question);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				pst.close();
				connection.close();
			} catch (Exception e) {				
			}
		}
		return session;
	}

	public static UserSession create(UserSession session) {
		PreparedStatement pst = null;
		String username = addReviewServlet.encodeHTML(session.getUsername());
		String password = session.getPassword();
		String question = addReviewServlet.encodeHTML(session.getQuestion());
		String answer = session.getAnswer();

		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("iso-8859-1"), 0, password.length());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] md5hash = new byte[32];
		md5hash = md.digest();
		password = convertToHex(md5hash);
		System.out.println("hashed: " + password);

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "INSERT INTO users (uname, pw, adminrole, question, answer) VALUES (?, ?, ?, ?, ?);";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setBoolean(3, false);
			pst.setString(4, question);
			pst.setString(5, answer);
			int result =  pst.executeUpdate();
			session.setLoggedIn(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e) {				
			}
			try {
				connection.close();
			} catch (Exception e) {				
			}
		}
		return session;
	}

	private static Connection createConnection() {
		Connection c = null;
		try {
			DataSource ds = (DataSource) new InitialContext().lookup("jdbc/lut2");
			c = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static UserSession resetPassword(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();
		String password = session.getPassword();
		String answer = session.getAnswer();
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("iso-8859-1"), 0, password.length());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] md5hash = new byte[32];
		md5hash = md.digest();
		password = convertToHex(md5hash);

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=? AND answer=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, answer);
			resultSet = pst.executeQuery();
			// check result
			if(resultSet.next()){
				sqlQuery = "UPDATE users SET pw=? WHERE uname=? AND answer=?";
				pst = connection.prepareStatement(sqlQuery);
				pst.setString(1, password);
				pst.setString(2, username);
				pst.setString(3, answer);
				pst.executeUpdate();
				session.setLoggedIn(true);
			} else {
				session.setLoggedIn(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				pst.close();
				connection.close();
			} catch (Exception e) {				
			}
		}
		return session;
	}
	
	private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
}
