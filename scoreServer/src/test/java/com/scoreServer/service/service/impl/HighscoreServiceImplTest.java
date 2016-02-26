package com.scoreServer.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.bean.UserScore;
import com.scoreServer.server.testUtils.GameDataMockHelper;
import com.scoreServer.server.testUtils.ServiceLocatorMockHelper;
import com.scoreServer.service.service.HighscoreFormatterService;

public class HighscoreServiceImplTest {
	
	private HighscoreServiceImpl highscoreService;
	
	private HighscoreFormatterService highscoreFormatterServiceMock;
	
	@Captor
	private ArgumentCaptor<Collection<UserScore>> collectionUserScoreArgumentCaptor;
		
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		ServiceLocatorMockHelper.clearRegistredInstances();
		highscoreFormatterServiceMock = ServiceLocatorMockHelper.mockAndRegister(HighscoreFormatterService.class);
		highscoreService = new HighscoreServiceImpl();
	}

	@Test
	public void testDataPassedToFormatter() {
		UserScore userScore1 = new UserScore(1, 15);
		UserScore userScore2 = new UserScore(2, 14);
		GameDataMockHelper.clearAndSet(11, userScore1);
		GameDataMockHelper.add(11, userScore2);
		
		highscoreService.getHighscoreForLevel("11");
		verify(highscoreFormatterServiceMock).format(collectionUserScoreArgumentCaptor.capture());
		assertEquals(2, collectionUserScoreArgumentCaptor.getValue().size());
		UserScore firstScoreInFormatterArgument = collectionUserScoreArgumentCaptor.getValue().iterator().next();
		assertEquals(1, firstScoreInFormatterArgument.getUserId(), 0);
		assertEquals(15, firstScoreInFormatterArgument.getScore(), 0);
	}
	
	@Test
	public void testSuccesfullHighscoreForLevel() {
		UserScore userScore1 = new UserScore(1, 15);
		UserScore userScore2 = new UserScore(2, 14);
		GameDataMockHelper.clearAndSet(11, userScore1);
		GameDataMockHelper.add(11, userScore2);
		
		ServiceResponse highscoreResponse = highscoreService.getHighscoreForLevel("11");
		assertEquals(200, highscoreResponse.getStatus());
		assertFalse(highscoreResponse.isError());
	}
	
	@Test
	public void testUnsuccesfullHighscoreForLevel() {
		ServiceResponse highscoreResponse = highscoreService.getHighscoreForLevel("abc");
		assertEquals(422, highscoreResponse.getStatus());
		assertTrue(highscoreResponse.isError());
	}
}
