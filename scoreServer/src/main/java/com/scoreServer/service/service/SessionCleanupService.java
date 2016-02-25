package com.scoreServer.service.service;

import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.SessionCleanupServiceImpl;

@Implementor(SessionCleanupServiceImpl.class)
public interface SessionCleanupService {
	void cleanUpSessions();
}
