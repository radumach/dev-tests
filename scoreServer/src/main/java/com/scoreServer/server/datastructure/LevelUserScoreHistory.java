package com.scoreServer.server.datastructure;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.scoreServer.ScoreServer;

public class LevelUserScoreHistory {

	private final ConcurrentHashMap<Integer, Integer> scoresHistory;
	private final TreeSet<UserScore> highscores;

	private int minHighScore = Integer.MIN_VALUE;

	final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public LevelUserScoreHistory() {
		scoresHistory = new ConcurrentHashMap<>();
		highscores = new TreeSet<>((o1, o2) -> o2.getScore() - o1.getScore());
	}

	public void update(UserScore newUserScore) {
		lock.writeLock().lock();
		boolean isHighscore = newUserScore.getScore() > minHighScore;
		if (isHighscore) {
			minHighScore = newUserScore.getScore();
		}
		if (scoresHistory.put(newUserScore.getUserId(), newUserScore.getScore()) != null) {
			// user was already in history
			if (isHighscore || highscores.size() <= ScoreServer.MAX_HIGHSCORE_COUNT) {
				removeOldHighscore(newUserScore);
				highscores.add(newUserScore);
				if (highscores.size() > ScoreServer.MAX_HIGHSCORE_COUNT) {
					removeLastHighscore();
				}

			}
		} else {
			// user wasn't in history
			if (isHighscore || highscores.size() <= ScoreServer.MAX_HIGHSCORE_COUNT) {
				highscores.add(newUserScore);
				if (highscores.size() > ScoreServer.MAX_HIGHSCORE_COUNT) {
					removeLastHighscore();
				}
			}
		}
		lock.writeLock().unlock();
	}

	public String getHighScoreList() {
		lock.readLock().lock();
		Iterator<UserScore> iterator = highscores.iterator();
		StringBuilder sb = new StringBuilder(iterator.next().toString());
		for (; iterator.hasNext();) {
			sb.append(", ");
			sb.append(iterator.next().toString());
		}
		lock.readLock().unlock();
		return sb.toString();
	}

	private void removeLastHighscore() {
		if (highscores.size() > ScoreServer.MAX_HIGHSCORE_COUNT) {
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
