package com.loadBalancer.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.loadBalancer.server.filter.ParamsFilter;
import com.loadBalancer.server.handler.RequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class LoadBalancer {
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		HttpContext context = server.createContext("/", new RequestHandler());

		context.getFilters().add(new ParamsFilter());

		// Set an Executor for the multi-threading
		server.setExecutor(Executors.newCachedThreadPool());

		// Start the server
		server.start();
		System.out.println("The server is started!");
	}
}