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
				System.out.println("New request received to save the score" + (String) params.get(Constants.POST_BODY_PARAMETER_NAME));

				int userId = -1;

				ScoreService scoreService = Context.get(ScoreService.class);

				serviceResponse = scoreService.addScore((String) params.get("sessionkey"),
						(String) params.get("levelid"), (String) params.get(Constants.POST_BODY_PARAMETER_NAME));

			} else if (params.get("request").equals("highscorelist")) {

				// A list of the best scores has been requested
				System.out.println("Received a new request for the highest scores" + "the level "
						+ (String) params.get("levelid"));
				
				HighscoreService highscoreService = Context.get(HighscoreService.class);
				serviceResponse = highscoreService.getHighscoreForLevel((String) params.get("levelid"));

				/*
				 * ScoreSingleton.getInstance().getHighestScores(
				 * Integer.parseInt((String)params.get("levelid")));
				 */

				// This is a header to permit the download of the csv
				Headers headers = exchange.getResponseHeaders();
				headers.add("Content-Type", "application/octet-stream");
				headers.add("Content-Disposition", "attachment;filename=" + "highscores.csv");
			} else {
				serviceResponse = new ServiceResponse("Not Available!", 400);
				System.out.println(serviceResponse);
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
				Headers headers = exchange.getResponseHeaders();
				headers.add("Content-Type", "text/plain");
				exchange.sendResponseHeaders(serviceResponse.getStatus(), serviceResponse.getResponse().length());
			} else {
				exchange.sendResponseHeaders(400, 0);
			}
		} catch (IOException e) {
			serviceResponse.setResponse(e.getMessage().toString());
			System.out.println(serviceResponse);
		}

		// Send the body response
		OutputStream os = exchange.getResponseBody();
		try {
			os.write(serviceResponse.getResponse().getBytes());
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
