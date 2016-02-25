package com.scoreServer.service.service.impl;

import static com.scoreServer.server.Constants.GAME_DATA;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.datastructure.LevelUserScoreHistory;
import com.scoreServer.server.util.SessionUtil;
import com.scoreServer.service.service.HighscoreService;

public class HighscoreServiceImpl implements HighscoreService {

	@Override
	public ServiceResponse getHighscoreForLevel(String levelIdString) {

		ServiceResponse response = null;

		Integer levelId = null;

		try {
			levelId = Integer.parseUnsignedInt(levelIdString);
		} catch (NumberFormatException e) {
			response = new ServiceResponse(e.getMessage(), 500);
		}

		if (levelId == null) {
			return new ServiceResponse("Invalid level!", 400);
		}
		
		Constants.GAME_HISTORY_LOCK.readLock().lock();
		
		try {
		
			LevelUserScoreHistory levelHistory = GAME_DATA.get(levelId);
	
			if (levelHistory == null) {
				return new ServiceResponse("Invalid level!", 400);
			}
			
			String highscoreList = levelHistory.getHighScoreList();
	
			String filename;
			
			try {
				filename = writeToFile(highscoreList);
			} catch (IOException e) {
				return new ServiceResponse("Error while exporting to csv!", 500);
			}
			
			response = new ServiceResponse(filename);
		
		} finally {
			Constants.GAME_HISTORY_LOCK.readLock().unlock();
		}
		
		return response;
	}

	private String writeToFile(String highscoreList) throws IOException {
		String fileName = SessionUtil.getSessionId() + ".csv";
		
		List<String> lines = Arrays.asList(highscoreList);
		Path file = Paths.get("the-file-name.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		return fileName;
		
	}

}
