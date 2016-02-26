package com.scoreServer.service.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import junit.framework.AssertionFailedError;

public class HighscoreServiceImplTest {
	
	private HighscoreServiceImpl highscoreService;
	
	private HighscoreFormatterService highscoreFormatterServiceMock;
	
	@Captor
	private ArgumentCaptor<Collection<UserScore>> collectionUserScoreArgumentCaptor;
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		highscoreFormatterServiceMock = ServiceLocatorMockHelper.mockAndRegister(HighscoreFormatterService.class);
		//when(highscoreFormatterServiceMock.format(any())).thenReturn("1,15\n2,14\n");
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
		//TODO
	}
}
