package com.scoreServer.server;

import com.scoreServer.service.framework.Context;
import com.scoreServer.service.service.SessionCleanupService;

public class SessionCleanupThread extends Thread {
	
	private SessionCleanupService cleanupService;

	@Override
	public void run() {
		
		cleanupService = Context.get(SessionCleanupService.class);

		while (true) {
			cleanupService.cleanUpSessions();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
