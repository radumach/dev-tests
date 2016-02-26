package com.scoreServer.service.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.testUtils.GameDataMockHelper;
import com.scoreServer.server.testUtils.SessionDataMockHelper;

public class ScoreServiceImplTest {

	private ScoreServiceImpl scoreServiceImpl;
	
	@Before
	public void init() {
		GameDataMockHelper.clearAll();
		SessionDataMockHelper.clearAll();
		scoreServiceImpl = new ScoreServiceImpl();
	}
	
	@Test
	public void testAddValidScore() {
		String sessionId = SessionDataMockHelper.startSession(1);
		ServiceResponse serviceResponse = scoreServiceImpl.addScore(sessionId, "11", "43");
		assertEquals(200, serviceResponse.getStatus());
		assertTrue(serviceResponse.getResponse().isEmpty());
		assertEquals(43, GameDataMockHelper.getScore(sessionId, 11), 0);
	}
	
	@Test
	public void testInvalidSessionId() {
		ServiceResponse serviceResponse = scoreServiceImpl.addScore("aaaaaaaa", "11", "43");
		assertEquals(401, serviceResponse.getStatus());
	}
	
	@Test
	public void testInvalidUserId() {
		String sessionId = SessionDataMockHelper.startSession(1);
		ServiceResponse serviceResponse = scoreServiceImpl.addScore(sessionId, "aa", "43");
		assertEquals(422, serviceResponse.getStatus());
	}
	
	@Test
	public void testInvalidScore() {
		String sessionId = SessionDataMockHelper.startSession(1);
		ServiceResponse serviceResponse = scoreServiceImpl.addScore(sessionId, "11", "aa");
		assertEquals(422, serviceResponse.getStatus());
	}
}
