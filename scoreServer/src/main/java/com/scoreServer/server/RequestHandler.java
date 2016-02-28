package com.scoreServer.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.Context;
import com.scoreServer.service.service.RequestHandlerService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) {

		ServiceResponse serviceResponse = null;
		
		RequestHandlerService requestHandler = Context.get(RequestHandlerService.class);

		try {
			Map<String, Object> params = (Map<String, Object>) exchange.getAttribute("parameters");

			if (params.get("request").equals("login")) {
				serviceResponse = requestHandler.doLogin(params);
			} else if (params.get("request").equals("score")) {
				serviceResponse = requestHandler.doScore(params);
			} else if (params.get("request").equals("highscorelist")) {
				serviceResponse = requestHandler.doHighscoreList(params);
			} else {
				serviceResponse = new ServiceResponse(HttpStatus.NOT_AVAILABLE_404.getMessage(),
						HttpStatus.NOT_AVAILABLE_404);
				System.out.println(serviceResponse);
			}
			
			Headers headers = exchange.getResponseHeaders();
			headers.add("Content-Type", "text/plain");
			
		} catch (NumberFormatException exception) {
			serviceResponse = new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(),
					HttpStatus.UNPROCESSABLE_ENTITY_422);
			System.out.println(serviceResponse.getResponse());
		} catch (Exception exception) {
			serviceResponse = new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(),
					HttpStatus.UNPROCESSABLE_ENTITY_422);
			System.out.println(serviceResponse.getResponse());
		}
		
		// Send the header response
		try {
			if (serviceResponse != null) {
				exchange.sendResponseHeaders(serviceResponse.getStatus(), serviceResponse.getResponse().length());
			} else {
				exchange.sendResponseHeaders(400, 0);
			}
		} catch (IOException e) {
			serviceResponse = new ServiceResponse(e.getMessage(), HttpStatus.BAD_REQUEST_400);
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
