package com.mashuq.athenaeum.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class SanitizationUtil {
	
	public static String sanitize(String input) {
		return Jsoup.clean(input, Whitelist.none());
	}
}
