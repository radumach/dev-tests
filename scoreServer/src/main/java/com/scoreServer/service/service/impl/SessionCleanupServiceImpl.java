package com.scoreServer.service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.Session;
import com.scoreServer.service.service.SessionCleanupService;

public class SessionCleanupServiceImpl implements SessionCleanupService{
	
	@Override
	public void cleanUpSessions() {
		long currentTimestamp = System.currentTimeMillis();
		List<Session> expiredSessions = Constants.SESSION_STORE.values().stream()
				.filter(s -> currentTimestamp - s.getTimestamp() >= Constants.SESSION_EXPIRATION_TIME.longValue())
				.collect(Collectors.toList());
		
		for (Session session : expiredSessions) {
			Constants.SESSION_STORE.remove(session);
			Constants.SESSION_MAP.remove(session.getSessionId());
		}
	}

}
