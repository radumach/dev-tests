package com.scoreServer.server.testUtils;

import static com.scoreServer.server.Constants.SESSION_MAP;
import static com.scoreServer.server.Constants.SESSION_STORE;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.Session;
import com.scoreServer.server.util.SessionUtil;

public class SessionDataMockHelper {

	
	public static void clearAll() {
		Constants.SESSION_MAP.clear();
		Constants.SESSION_STORE.clear();
	}
	
	public static String startSession(int userId) {
		String sessionId =  SessionUtil.getSessionId();
		SESSION_STORE.put(sessionId, new Session(sessionId));
		SESSION_MAP.put(sessionId, userId);
		return sessionId;
	}
}
