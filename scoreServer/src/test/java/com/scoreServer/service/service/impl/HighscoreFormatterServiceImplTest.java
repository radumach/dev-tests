package com.scoreServer.service.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.service.service.impl.HighscoreFormatterServiceImpl;

public class HighscoreFormatterServiceImplTest {

	private HighscoreFormatterServiceImpl highscoreFormatterService = new HighscoreFormatterServiceImpl();
	
	@Test
	public void testFormatEmptyHighscores() {
		String formattedHighscores = highscoreFormatterService.format(new ArrayList<>());
		assertEquals("", formattedHighscores);
	}
	
	@Test
	public void testFromat() {
		List<UserScore> highscores = new ArrayList<>();
		highscores.add(new UserScore(1, 15));
		highscores.add(new UserScore(2, 14));
		String formattedHighscores = highscoreFormatterService.format(highscores);
		assertEquals("1" + Constants.HIGHSCORE_FORMAT_VALUE_SEPARATOR + "15"+Constants.HIGHSCORE_FORMAT_LINE_SEPARATOR + 
				"2" + Constants.HIGHSCORE_FORMAT_VALUE_SEPARATOR + "14",
				formattedHighscores);
	}
}
