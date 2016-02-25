package com.scoreServer.service.service.impl;

import static com.scoreServer.server.Constants.GAME_DATA;
import static com.scoreServer.server.Constants.SESSION_MAP;
import static com.scoreServer.server.Constants.SESSION_STORE;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;
import com.scoreServer.service.service.ScoreService;

public class ScoreServiceImpl implements ScoreService {
	
	private boolean validateSession(String sessionId) {
		return SESSION_STORE.get(sessionId) != null;
	}

	@Override
	public ServiceResponse addScore(String sessionId, String levelIdString, String scoreString) {
		ServiceResponse response;

		Integer levelId = null;
		Integer score = null;

		try {
			levelId = Integer.parseUnsignedInt(levelIdString);
			score = Integer.parseUnsignedInt(scoreString);
		} catch (NumberFormatException e) {
			response = new ServiceResponse(e.getMessage(), 500);
		}

		if (levelId == null) {
			return new ServiceResponse("Invalid level!", 400);
		}

		if (!validateSession(sessionId)) {
			return new ServiceResponse("Invalid session! Please call login.", 401);
		}
		
		Constants.GAME_HISTORY_LOCK.writeLock().lock();
		
		try {

			Integer userId = SESSION_MAP.get(sessionId);
			UserScore userScore = new UserScore(userId, score);
	
			LevelUserScoreHistory levelHistory = GAME_DATA.get(levelId);
	
			if (levelHistory == null) {
				levelHistory = new LevelUserScoreHistory();
				GAME_DATA.put(levelId, levelHistory);
			}
	
			levelHistory.update(userScore);
	
			response = new ServiceResponse("");
			
		} finally {
			Constants.GAME_HISTORY_LOCK.writeLock().unlock();
		}

		return response;
	}

}
