package core.utils;

import javax.servlet.http.HttpSession;

import core.UserSession;

public class SecurityHelper {
	public static boolean isContainDangerousChars(String str) {
		if (str.indexOf('=') != -1) return true;
		if (str.indexOf('<') != -1) return true;
		if (str.indexOf('>') != -1) return true;
		if (str.indexOf('/') != -1) return true;
		if (str.indexOf('\'') != -1) return true;
		if (str.indexOf('"') != -1) return true;
		if (str.indexOf('&') != -1) return true;
		if (str.indexOf('(') != -1) return true;
		if (str.indexOf(')') != -1) return true;
		if (str.indexOf('-') != -1) return true;
		
		return false;
	}
	
	public static boolean isAdminLoggedIn(HttpSession session) {
		UserSession user = (UserSession)session.getAttribute("currentUser");
		if (user == null /*|| user.isAdminRole() == false*/) {
			return false;
		} else {
			return true;
		}
	}
}
