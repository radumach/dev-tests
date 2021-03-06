package com.scoreServer.server.testUtils;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;

public class GameDataMockHelper {
	
	public static void clearAll() {
		Constants.GAME_DATA.clear();
	}

	public static void clearAndSet(ConcurrentHashMap<Integer, LevelUserScoreHistory> gameData) {
		Constants.GAME_DATA.clear();
		Constants.GAME_DATA.putAll(gameData);
	}
	
	public static void clearAndSet(int userId, LevelUserScoreHistory levelUserScoreHistory) {
		Constants.GAME_DATA.clear();
		Constants.GAME_DATA.put(userId, levelUserScoreHistory);
	}
	
	public static void clearAndSet(int levelId, UserScore... userScores) {
		Constants.GAME_DATA.clear();
		LevelUserScoreHistory levelUserScoreHistory = new LevelUserScoreHistory();
		for(UserScore us:userScores) {
			levelUserScoreHistory.update(us);
		}
		Constants.GAME_DATA.put(levelId, levelUserScoreHistory);
	}
	
	public static void add(int levelId, LevelUserScoreHistory levelUserScoreHistory) {
		Constants.GAME_DATA.put(levelId, levelUserScoreHistory);
	}
	
	public static void add(int levelId, UserScore... userScores) {
		LevelUserScoreHistory levelUserScoreHistory = Constants.GAME_DATA.get(levelId);
		if(levelUserScoreHistory == null) {
			levelUserScoreHistory = new LevelUserScoreHistory();
			Constants.GAME_DATA.put(levelId, levelUserScoreHistory);
		}
		for(UserScore us:userScores) {
			levelUserScoreHistory.update(us);
		}
	}
	
	public static Integer getScore(String sessionId, int levelId) {
		LevelUserScoreHistory levelUserScoreHistory = Constants.GAME_DATA.get(levelId);
		if(levelUserScoreHistory == null) {
			return null;
		}
		Integer userId = Constants.SESSION_MAP.get(sessionId);
		Optional<UserScore> o = levelUserScoreHistory.getHighscores().stream().filter(us -> us.getUserId().equals(userId)).findFirst();
		if(o.isPresent()) {
			return o.get().getScore();
		} 
		
		return null;
	}
}
