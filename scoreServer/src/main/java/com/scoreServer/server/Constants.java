package com.scoreServer.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.scoreServer.server.bean.Session;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;

public class Constants {
	
	public static final ConcurrentHashMap<String, Session> SESSION_STORE = new ConcurrentHashMap<String, Session>();

	public static final ConcurrentHashMap<String, Integer> SESSION_MAP = new ConcurrentHashMap<String, Integer>();

	public static final ConcurrentHashMap<Integer, LevelUserScoreHistory> GAME_DATA = new ConcurrentHashMap<Integer, LevelUserScoreHistory>();

	public static Integer MAX_HIGHSCORE_COUNT;

	public static Long SESSION_EXPIRATION_TIME;

}
