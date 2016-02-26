package com.scoreServer.server.util;

import java.util.Random;

public class SessionUtil {
	
	public static String getSessionId() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}

}
