package com.scoreServer.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.scoreServer.server.bean.Session;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;

public class Constants {
	
	public static final int SERVER_PORT = 8000;
	
	public static final ConcurrentHashMap<String, Session> SESSION_STORE = new ConcurrentHashMap<String, Session>();

	public static final ConcurrentHashMap<String, Integer> SESSION_MAP = new ConcurrentHashMap<String, Integer>();

	public static final ConcurrentHashMap<Integer, LevelUserScoreHistory> GAME_DATA = new ConcurrentHashMap<Integer, LevelUserScoreHistory>();

	public static Integer MAX_HIGHSCORE_COUNT = 15;

	public static Long SESSION_EXPIRATION_TIME = 10 * 60000l; // 10 minutes
	
	public static final ReentrantReadWriteLock GAME_HISTORY_LOCK = new ReentrantReadWriteLock();
	
	public static final String POST_BODY_PARAMETER_NAME = "POST_BODY";
	
	public static final String HIGHSCORE_FORMAT_VALUE_SEPARATOR = "=";
	
	public static final String HIGHSCORE_FORMAT_LINE_SEPARATOR = ",";
	
	public static enum HttpStatus {
		
		OK_200(200, "OK"),
		CREATED_201(201, "Created"),
		NO_CONTENT_204(200, "No content"),
		BAD_REQUEST_400(400, "Bad request"),
		BAD_REQUEST_401(401, "Unauthorized"),
		FORBIDDEN_403(403, "Forbidden"),
		NOT_AVAILABLE_404(404, "Not available"),
		UNPROCESSABLE_ENTITY_422(422, "Unprocessable Entity");
		
		private final int code;
		private final String message;
		
		private HttpStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
		
		public String toString() {
			return code + " - " + message;
		}
	}

	public static final boolean IS_DOWNLOADABLE = false;
}
