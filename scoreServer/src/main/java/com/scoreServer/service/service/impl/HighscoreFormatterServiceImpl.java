package com.scoreServer.service.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.service.service.HighscoreFormatterService;

public class HighscoreFormatterServiceImpl implements HighscoreFormatterService {

	@Override
	public String format(Collection<UserScore> highscores) {
		if(highscores.isEmpty()) {
			return "";
		}
		return highscores.stream().map(
				userScore -> new StringBuilder()
					.append(userScore.getUserId())
					.append(Constants.HIGHSCORE_FORMAT_VALUE_SEPARATOR)
					.append(userScore.getScore()))
						.collect(Collectors.joining(Constants.HIGHSCORE_FORMAT_LINE_SEPARATOR));
		
	}

}
