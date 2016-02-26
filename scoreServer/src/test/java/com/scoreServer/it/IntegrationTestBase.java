package com.scoreServer.it;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.mashape.unirest.http.HttpResponse;
import com.scoreServer.ScoreServer;
import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.testUtils.GameDataMockHelper;
import com.scoreServer.server.testUtils.ScoreClient;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
//		checkServerRunning();
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
//		checkServerStopped();
		SessionDataMockHelper.clearAll();
		GameDataMockHelper.clearAll();
	}
	
	private static void checkServerRunning() {
		int i = 0;
		boolean alive = false;
		while(i++ < 10) {
			try {
				Thread.sleep(200);
				HttpResponse<String> response =  ScoreClient.login("1");
				if(response.getStatus() == HttpStatus.OK_200.getCode()) {
					alive = true;
					break;
				}
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}
		if(!alive) {
			throw new RuntimeException("Server didn't start!");
		}
	}
	
	private static void checkServerStopped() {
		int i = 0;
		boolean alive = true;
		while(i++ < 10) {
			try {
				Thread.sleep(200);
				ScoreClient.login("1");
			} catch(Throwable t) {
				alive = false;
				break;
			}
		}
		if(alive) {
			throw new RuntimeException("Server didn't stop!");
		}
	}
}
