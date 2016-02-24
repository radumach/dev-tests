package com.scoreServer;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import com.scoreServer.server.PramsFilter;
import com.scoreServer.server.RequestHandler;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 *
 */
public class ScoreServer {

	public static final ConcurrentHashMap<Integer, String> SESSION_STORE = new ConcurrentHashMap<Integer, String>();

	public static final ConcurrentHashMap<Integer, LevelUserScoreHistory> GAME_DATA = new ConcurrentHashMap<Integer, LevelUserScoreHistory>();

	public static final int MAX_HIGHSCORE_COUNT = 15;

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

}
