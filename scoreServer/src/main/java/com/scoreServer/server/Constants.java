package com.scoreServer.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.scoreServer.server.bean.Session;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;

public class Constants {
	
	public static final ConcurrentHashMap<String, Session> SESSION_STORE = new ConcurrentHashMap<String, Session>();

	public static final ConcurrentHashMap<String, Integer> SESSION_MAP = new ConcurrentHashMap<String, Integer>();

	public static final ConcurrentHashMap<Integer, LevelUserScoreHistory> GAME_DATA = new ConcurrentHashMap<Integer, LevelUserScoreHistory>();

	public static Integer MAX_HIGHSCORE_COUNT = 15;

	public static Long SESSION_EXPIRATION_TIME = 10 * 60000l; // 10 minutes
	
	public static final ReentrantReadWriteLock GAME_HISTORY_LOCK = new ReentrantReadWriteLock();
	
	public static final String POST_BODY_PARAMETER_NAME = "POST_BODY";

}
