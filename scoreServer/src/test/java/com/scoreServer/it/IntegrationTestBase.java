package com.scoreServer.it;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.scoreServer.ScoreServer;
import com.scoreServer.server.testUtils.GameDataMockHelper;
import com.scoreServer.server.testUtils.SessionDataMockHelper;

public class IntegrationTestBase {

	@BeforeClass
	public static void init() throws Exception {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ScoreServer.main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	@Before
	@After
	public void reset() {
		SessionDataMockHelper.clearAll();
		GameDataMockHelper.clearAll();
	}
	
	@AfterClass
	public static void shutDown() throws Exception {
		ScoreServer.stop();
		SessionDataMockHelper.clearAll();
		GameDataMockHelper.clearAll();
	}
	
}
