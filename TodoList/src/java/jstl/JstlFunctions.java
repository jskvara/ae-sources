package jstl;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class JstlFunctions {
	private JstlFunctions() {}

	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
}
