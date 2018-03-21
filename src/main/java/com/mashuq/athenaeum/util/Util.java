package com.mashuq.athenaeum.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

	public static String getCurrentTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
