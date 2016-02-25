package com.scoreServer.service.service;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.HighscoreServiceImpl;

@Implementor(HighscoreServiceImpl.class)
public interface HighscoreService {

	ServiceResponse getHighscoreForLevel(String levelId);

}
