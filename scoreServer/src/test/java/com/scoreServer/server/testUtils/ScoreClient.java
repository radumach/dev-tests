package com.scoreServer.server.testUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreServer.server.Constants;

public class ScoreClient {
	
	public static HttpResponse<String> login(int userId) throws UnirestException {
		return Unirest.get("http://127.0.0.1:" + Constants.SERVER_PORT + "/" + userId + "/login").asString();
	}
}
