package com.scoreServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.scoreServer.server.Constants;
import com.scoreServer.server.ParamsFilter;
import com.scoreServer.server.RequestHandler;
import com.scoreServer.server.SessionCleanupThread;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 *
 */
public class ScoreServer {
	
	private static HttpServer server;

	public static void main(String... args) throws Exception {

		if (args.length >= 2) {
			Constants.MAX_HIGHSCORE_COUNT = Integer.parseInt(args[0]);
			Constants.SESSION_EXPIRATION_TIME = 60000l * Long.parseLong(args[1]);
		}

		// Create an http server
		server = HttpServer.create(new InetSocketAddress("localhost", Constants.SERVER_PORT), 0);

		// Create a context
		HttpContext context = server.createContext("/", new RequestHandler());

		// Add a filter
		context.getFilters().add(new ParamsFilter());

		// Set an Executor for the multi-threading
		server.setExecutor(Executors.newCachedThreadPool());

		// Start the server
		server.start();
		System.out.println("Server is started: " + server.getAddress());
		
		Thread sessionCleanupThread = new SessionCleanupThread();
		sessionCleanupThread.setDaemon(true);
		sessionCleanupThread.start();
		System.out.println("Session cleanup thread is started!");
		
	}
	
	public static void stop() {
		server.stop(0);
	}

}
