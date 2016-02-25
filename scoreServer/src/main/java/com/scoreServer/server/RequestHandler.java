package com.scoreServer.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.Context;
import com.scoreServer.service.service.HighscoreService;
import com.scoreServer.service.service.LoginService;
import com.scoreServer.service.service.ScoreService;
import com.sun.net.httpserver.*;

public class RequestHandler implements HttpHandler {

	/*
	 * Usage: handle a client request
	 * 
	 * Input: exchange = request/response object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) {

		String response = null;
		int statusCode = 200;

		ServiceResponse serviceResponse = null;

		try {
			Map<String, Object> params = (Map<String, Object>) exchange.getAttribute("parameters");

			if (params.get("request").equals("login")) {

				String userId = (String) params.get("userid");

				// The user is logging-in, asking for a session key
				// GetSession for user

				LoginService loginService = Context.get(LoginService.class);

				serviceResponse = loginService.login(userId);

			} else if (params.get("request").equals("score")) {

				// A new store has been received to be stored
				System.out.println("New request received to save the score" + (String) params.get("score"));

				int userId = -1;

				ScoreService scoreService = Context.get(ScoreService.class);

				serviceResponse = scoreService.addScore((String) params.get("sessionkey"),
						(String) params.get("levelid"), (String) params.get("score"));

			} else if (params.get("request").equals("highscorelist")) {

				// A list of the best scores has been requested
				System.out.println("Received a new request for the highest scores" + "the level "
						+ (String) params.get("levelid"));

				// get highscore per level
				response = null;

				HighscoreService highscoreService = Context.get(HighscoreService.class);
				String fileName = highscoreService.getHighscoreForLevel((String) params.get("levelid")).getResponse();

				/*
				 * ScoreSingleton.getInstance().getHighestScores(
				 * Integer.parseInt((String)params.get("levelid")));
				 */

				// This is a header to permit the download of the csv
				Headers headers = exchange.getResponseHeaders();
				headers.add("Content-Type", "text/csv");
				headers.add("Content-Disposition", "attachment;filename=" + fileName);
			} else {
				response = "Not Available!";
				System.out.println(response);
				statusCode = 400; // Request type not implemented
			}
		} catch (NumberFormatException exception) {
			serviceResponse.setStatus(400);
			serviceResponse.setResponse("Wrong number format");
			System.out.println(serviceResponse.getResponse());
		} catch (Exception exception) {
			serviceResponse.setStatus(400);
			serviceResponse.setResponse("Wrong number format");
			System.out.println(serviceResponse.getResponse());
		}

		// Send the header response

		try {
			if (serviceResponse != null) {
				exchange.sendResponseHeaders(serviceResponse.getStatus(), serviceResponse.getResponse().length());
			} else {
				exchange.sendResponseHeaders(statusCode, 0);
			}
		} catch (IOException e) {
			serviceResponse.setResponse(e.getMessage().toString());
			System.out.println(response);
		}

		// Send the body response
		OutputStream os = exchange.getResponseBody();
		try {
			os.write(response.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
