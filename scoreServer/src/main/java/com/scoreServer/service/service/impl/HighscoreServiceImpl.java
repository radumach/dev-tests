package com.scoreServer.service.service.impl;

import static com.scoreServer.server.Constants.GAME_DATA;

import java.util.List;

import com.scoreServer.server.Constants;
import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;
import com.scoreServer.service.framework.Context;
import com.scoreServer.service.service.HighscoreFormatterService;
import com.scoreServer.service.service.HighscoreService;

public class HighscoreServiceImpl implements HighscoreService {
	
	private HighscoreFormatterService highscoreFormatterService;
	
	public HighscoreServiceImpl() {
		highscoreFormatterService = Context.get(HighscoreFormatterService.class);
	}

	@Override
	public ServiceResponse getHighscoreForLevel(String levelIdString) {

		ServiceResponse response = null;

		Integer levelId = null;

		try {
			levelId = Integer.parseUnsignedInt(levelIdString);
		} catch (NumberFormatException e) {
			response = new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY_422);
		}

		if (levelId == null) {
			return new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY_422);
		}
		
		Constants.GAME_HISTORY_LOCK.readLock().lock();
		
		try {
		
			LevelUserScoreHistory levelHistory = GAME_DATA.get(levelId);
	
			if (levelHistory == null) {
				return new ServiceResponse(HttpStatus.NOT_AVAILABLE_404.getMessage(), HttpStatus.NOT_AVAILABLE_404);
			}
			
			List<UserScore> highscores = levelHistory.getHighscores();
			String highscoreList = highscoreFormatterService.format(highscores);
			
			response = new ServiceResponse(highscoreList);
		
		} finally {
			Constants.GAME_HISTORY_LOCK.readLock().unlock();
		}
		
		return response;
	}

}
