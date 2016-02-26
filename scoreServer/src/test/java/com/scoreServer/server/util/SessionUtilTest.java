package com.scoreServer.server.util;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

public class SessionUtilTest {

	@Test
	public void testGetSessionId() {
		TreeSet<String> sessionIds = new TreeSet<>();
		for(int i = 0; i < 100000; i++) {
			String sessionId = SessionUtil.getSessionId();
			assertTrue(isAllLetters(sessionId));
			assertTrue("Duplicate session id for " + sessionId, sessionIds.add(sessionId));
		}
	}
	
	private boolean isAllLetters(String string) {
		for(char c:string.toCharArray()) {
			if(!isLetter(c)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isLetter(char c) {
	    return (c >= 'a' && c <= 'z') ||
	           (c >= 'A' && c <= 'Z');
	}
}
