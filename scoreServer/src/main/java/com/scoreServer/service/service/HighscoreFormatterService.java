package com.scoreServer.service.service;

import java.util.Collection;

import com.scoreServer.server.bean.UserScore;
import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.HighscoreFormatterServiceImpl;

@Implementor(HighscoreFormatterServiceImpl.class)
public interface HighscoreFormatterService {

	public String format(Collection<UserScore> highscores);
}
