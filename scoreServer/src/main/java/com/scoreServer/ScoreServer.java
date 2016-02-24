package com.scoreServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import com.scoreServer.server.PramsFilter;
import com.scoreServer.server.RequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 *
 */
public class ScoreServer {
	
	public final static ConcurrentHashMap<Integer, String> SESSION_STORE = new ConcurrentHashMap<Integer, String>();
	
	public static final ConcurrentHashMap<Integer, Object> GAME_DATA = new ConcurrentHashMap<Integer, Object>();
	
	public static void main(String[] args) throws Exception {
		// Create an http server
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// Create a context
		HttpContext context = server.createContext("/", new RequestHandler());

		// Add a filter
		context.getFilters().add(new PramsFilter());

		// Set an Executor for the multi-threading
		server.setExecutor(Executors.newCachedThreadPool());

		// Start the server
		server.start();

		System.out.println("The server is started!");
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			String response = "This is the response";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
