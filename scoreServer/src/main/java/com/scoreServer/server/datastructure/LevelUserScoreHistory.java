package com.scoreServer.server.datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.UserScore;

public class LevelUserScoreHistory {

	private final Map<Integer, Integer> scoresHistory;
	private final TreeSet<UserScore> highscores;

	private int minHighScore = Integer.MIN_VALUE;

	public LevelUserScoreHistory() {
		scoresHistory = new HashMap<>();
		highscores = new TreeSet<>((o1, o2) -> o2.getScore() - o1.getScore());
	}

	public void update(UserScore newUserScore) {
		boolean isHighscore = newUserScore.getScore() > minHighScore;
		if (isHighscore) {
			minHighScore = newUserScore.getScore();
		}
		if (scoresHistory.put(newUserScore.getUserId(), newUserScore.getScore()) != null) {
			// user was already in history
			if (isHighscore || highscores.size() <= Constants.MAX_HIGHSCORE_COUNT) {
				removeOldHighscore(newUserScore);
				highscores.add(newUserScore);
				if (highscores.size() > Constants.MAX_HIGHSCORE_COUNT) {
					removeLastHighscore();
				}

			}
		} else {
			// user wasn't in history
			if (isHighscore || highscores.size() <= Constants.MAX_HIGHSCORE_COUNT) {
				highscores.add(newUserScore);
				if (highscores.size() > Constants.MAX_HIGHSCORE_COUNT) {
					removeLastHighscore();
				}
			}
		}
	}

	public String getHighScoreList() {
		Iterator<UserScore> iterator = highscores.iterator();
		StringBuilder sb = new StringBuilder(iterator.next().toString());
		for (; iterator.hasNext();) {
			sb.append(", ");
			sb.append(iterator.next().toString());
		}
		return sb.toString();
	}

	private void removeLastHighscore() {
		if (highscores.size() > Constants.MAX_HIGHSCORE_COUNT) {
			highscores.remove(highscores.last());
		}
	}

	private void removeOldHighscore(UserScore newUserScore) {
		NavigableSet<UserScore> matchingHighScores = highscores.subSet(newUserScore, true, newUserScore, true);
		Optional<UserScore> potentialOldHighscoreForUser = matchingHighScores.stream()
				.filter(userScore -> userScore.getUserId().equals(newUserScore.getUserId())).findFirst();
		if (potentialOldHighscoreForUser.isPresent()) {
			highscores.remove(potentialOldHighscoreForUser.get());
		}
	}
}
