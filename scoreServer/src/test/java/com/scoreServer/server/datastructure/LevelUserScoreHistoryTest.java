package com.scoreServer.server.datastructure;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.scoreServer.server.bean.UserScore;

public class LevelUserScoreHistoryTest {

	private LevelUserScoreHistory scoreHistory;
	
	@Before
	public void init() {
		scoreHistory = new LevelUserScoreHistory();
	}
	
	@Test
	public void testHighscoreOneUser() {
		scoreHistory.update(new UserScore(1, 55));
		assertEquals(1, scoreHistory.getHighscores().size());
		assertEquals(55, scoreHistory.getHighscores().iterator().next().getScore());
		
		scoreHistory.update(new UserScore(1, 33));
		assertEquals(1, scoreHistory.getHighscores().size());
		assertEquals(55, scoreHistory.getHighscores().iterator().next().getScore());
		
		scoreHistory.update(new UserScore(1, 66));
		assertEquals(1, scoreHistory.getHighscores().size());
		assertEquals(66, scoreHistory.getHighscores().iterator().next().getScore());
	}
	
	@Test
	public void testHighscoreTwoUsers() {
		scoreHistory.update(new UserScore(1, 55));
		scoreHistory.update(new UserScore(2, 33));
		
		assertEquals(2, scoreHistory.getHighscores().size());
		assertEquals(55, scoreHistory.getHighscores().get(0).getScore());
		assertEquals(33, scoreHistory.getHighscores().get(1).getScore());
	}
	
	@Test
	public void testHighscoreTwoUsersWithSameScore() {
		scoreHistory.update(new UserScore(1, 55));
		scoreHistory.update(new UserScore(2, 55));
		
		assertEquals(2, scoreHistory.getHighscores().size());
		assertEquals(55, scoreHistory.getHighscores().get(0).getScore());
		assertEquals(1, scoreHistory.getHighscores().get(0).getUserId(), 0);
		assertEquals(55, scoreHistory.getHighscores().get(1).getScore());
		assertEquals(2, scoreHistory.getHighscores().get(1).getUserId(), 0);
	}
	
	@Test
	public void testHighscoreListFull() {
		scoreHistory.update(new UserScore(1, 55));
		scoreHistory.update(new UserScore(2, 15));
		scoreHistory.update(new UserScore(3, 65));
		scoreHistory.update(new UserScore(4, 25));
		scoreHistory.update(new UserScore(5, 25));
		scoreHistory.update(new UserScore(6, 25));
		scoreHistory.update(new UserScore(7, 25));
		scoreHistory.update(new UserScore(8, 25));
		scoreHistory.update(new UserScore(9, 25));
		scoreHistory.update(new UserScore(10, 25));
		scoreHistory.update(new UserScore(11, 25));
		scoreHistory.update(new UserScore(12, 25));
		scoreHistory.update(new UserScore(13, 25));
		scoreHistory.update(new UserScore(14, 25));
		scoreHistory.update(new UserScore(15, 25));
		
		assertEquals(15, scoreHistory.getHighscores().size());
		assertEquals(65, scoreHistory.getHighscores().get(0).getScore());
		assertEquals(3, scoreHistory.getHighscores().get(0).getUserId(), 0);
		assertEquals(55, scoreHistory.getHighscores().get(1).getScore());
		assertEquals(1, scoreHistory.getHighscores().get(1).getUserId(), 0);
	}
}
