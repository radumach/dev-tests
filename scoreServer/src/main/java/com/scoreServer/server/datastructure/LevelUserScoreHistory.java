package com.scoreServer.server.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.UserScore;

public class LevelUserScoreHistory {

	private final List<UserScore> highscores;
	private Comparator<UserScore> highscoreComparator;

	private int minHighScore = Integer.MIN_VALUE;

	public LevelUserScoreHistory() {
		highscores = new ArrayList<>(Constants.MAX_HIGHSCORE_COUNT);
		highscoreComparator = (us1, us2) -> us2.getScore() - us1.getScore();
	}

	public void update(UserScore newUserScore) {
		updateHighscores(newUserScore);
	}

	public List<UserScore> getHighscores() {
		//TODO: maybe: ?
		//return Collections.unmodifiableList(highscores);
		return highscores;
	}
	
	private void updateHighscores(UserScore newUserScore) {
		if(newUserScore.getScore() > minHighScore) {
			Optional<UserScore> oldHighscore = getOldHighscore(newUserScore);
			if(oldHighscore.isPresent()) {
				UserScore oldHighscoreEntry = oldHighscore.get();
				if(oldHighscoreEntry.getScore() < newUserScore.getScore()) {
					oldHighscoreEntry.setScore(newUserScore.getScore());
					sortHighscores();
					updateMinHighscore();
				}
			} else {
				highscores.add(newUserScore);
				sortHighscores();
				trimHighscores();
				updateMinHighscore();
			}
		}
	}
	
	private void trimHighscores() {
		if(highscores.size() > Constants.MAX_HIGHSCORE_COUNT) {
			highscores.remove(highscores.size() - 1);
		}
	}
	
	private void updateMinHighscore() {
		if(highscores.size() >= Constants.MAX_HIGHSCORE_COUNT) {
			minHighScore = highscores.get(highscores.size() - 1).getScore();
		}
	}
	
	private void sortHighscores() {
		Collections.sort(highscores, highscoreComparator);
	}
	
	private Optional<UserScore> getOldHighscore(UserScore newUserScore) {
		return highscores.stream().filter(us -> us.getUserId().equals(newUserScore.getUserId())).findFirst();
	}
}
