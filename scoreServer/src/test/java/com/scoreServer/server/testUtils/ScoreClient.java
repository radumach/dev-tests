package com.scoreServer.server.testUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreServer.server.Constants;

public class ScoreClient {
	
	static {
		Unirest.setTimeouts(10000, 10000);
	}
	
	public static HttpResponse<String> login(String userId) throws UnirestException {
		return Unirest.get("http://127.0.0.1:" + Constants.SERVER_PORT + "/" + userId + "/login").asString();
	}
	
	public static HttpResponse<String> updateScore(String sessionId, String levelId, String score) throws UnirestException {
		return Unirest.post("http://127.0.0.1:" + Constants.SERVER_PORT + "/" + levelId + "/score?sessionkey="+sessionId)
				.body(score).asString();
	}
	
	public static HttpResponse<String> getHighscore(String levelId) throws UnirestException {
		return Unirest.get("http://127.0.0.1:" + Constants.SERVER_PORT + "/" + levelId + "/highscorelist").asString();
	} 
}
