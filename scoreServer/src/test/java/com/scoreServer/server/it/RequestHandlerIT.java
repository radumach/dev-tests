package com.scoreServer.server.it;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreServer.ScoreServer;
import com.scoreServer.server.Constants;
import com.scoreServer.server.testUtils.GameDataMockHelper;
import com.scoreServer.server.testUtils.ScoreClient;
import com.scoreServer.server.testUtils.SessionDataMockHelper;

public class RequestHandlerIT {
	
	@Before
	public void init() throws Exception {
		SessionDataMockHelper.clearAll();
		GameDataMockHelper.clearAll();
		
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
		Thread.sleep(2000);
	}
	
	@Before
	public void shutDown() throws Exception {
		SessionDataMockHelper.clearAll();
		GameDataMockHelper.clearAll();
		ScoreServer.stop();
	}

	@Test
	public void dummyTest() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login(1);
		assertEquals(200, response.getStatus());
	}
}
