package core.utils;

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
}
