package com.scoreServer.it;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreServer.server.testUtils.ScoreClient;

public class ScoreServerUseCaseIT extends IntegrationTestBase {

	@Test
	public void testHighscoreListForFewUsers() throws UnirestException {
		HttpResponse<String> response = ScoreClient.login("1");
		String sessionId1 = response.getBody();
		response = ScoreClient.updateScore(sessionId1, "11", "44");
		
		response = ScoreClient.login("2");
		String sessionId2 = response.getBody();
		response = ScoreClient.updateScore(sessionId2, "11", "62");
		
		response = ScoreClient.login("3");
		String sessionId3 = response.getBody();
		response = ScoreClient.updateScore(sessionId3, "11", "4");
		
		response = ScoreClient.getHighscore("11");
		String highscoresList = response.getBody();
		
		//TODO use separators from constants:
		assertEquals("2=62,1=44,3=4", highscoresList);
	}
	
	
}
