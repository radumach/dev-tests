package com.scoreServer.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreServer.server.Constants;
import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.testUtils.ScoreClient;

public class ScoreServerSmokeTestIT extends IntegrationTestBase {
	
	@Test
	public void testValidLogin() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login("1");
		assertEquals(200, response.getStatus());
		assertEquals(1, response.getHeaders().get("content-type").size());
		assertEquals("text/plain", response.getHeaders().get("content-type").get(0));
		assertFalse(response.getBody().trim().isEmpty());
	}
	
	@Test
	public void testInvalidUserIdLogin() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login("aa");
		assertEquals(422, response.getStatus());
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(), response.getBody());
	}
	
	@Test
	public void testUpdateScore() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login("1");
		String sessionId = response.getBody();
		response = ScoreClient.updateScore(sessionId, "11", "44");
		assertEquals(HttpStatus.OK_200.getCode(), response.getStatus());
		assertEquals(1, response.getHeaders().get("content-type").size());
		assertEquals("text/plain", response.getHeaders().get("content-type").get(0));
		assertTrue(response.getBody().isEmpty());
	}
	
	@Test
	public void testUpdateScoreInvalidSession() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login("1");
		response = ScoreClient.updateScore("aaaaaaaaaaa", "11", "44");
		assertEquals(HttpStatus.BAD_REQUEST_401.getCode(), response.getStatus());
		//TODO: maybe response body for this operation should be empty also on error ? (as it is for successful)
		assertFalse(response.getBody().isEmpty());
	}
	
	@Test
	public void testGetHighscore() throws UnirestException, InterruptedException {
		HttpResponse<String> response = ScoreClient.login("1");
		String sessionId = response.getBody();
		response = ScoreClient.updateScore(sessionId, "11", "44");
		response = ScoreClient.getHighscore("11");
		assertEquals(1, response.getHeaders().get("content-type").size());
		assertEquals("application/octet-stream", response.getHeaders().get("content-type").get(0));
		assertEquals(1, response.getHeaders().get("content-disposition").size());
		assertEquals("attachment;filename=" + "highscores.csv", response.getHeaders().get("content-disposition").get(0));
		assertEquals("1" + Constants.HIGHSCORE_FORMAT_VALUE_SEPARATOR + "44", response.getBody());
	}
	
	
}
