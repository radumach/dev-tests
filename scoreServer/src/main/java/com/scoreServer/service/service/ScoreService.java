package com.scoreServer.service.service;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.ScoreServiceImpl;

@Implementor(ScoreServiceImpl.class)
public interface ScoreService {

	ServiceResponse addScore(String sessionId, String levelId, String score);
}
