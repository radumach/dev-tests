package com.scoreServer.service.service.impl;

import java.util.Map;

import com.scoreServer.server.Constants;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.Context;
import com.scoreServer.service.service.HighscoreService;
import com.scoreServer.service.service.LoginService;
import com.scoreServer.service.service.RequestHandlerService;
import com.scoreServer.service.service.ScoreService;

public class RequestHandlerServiceImpl implements RequestHandlerService {

	@Override
	public ServiceResponse doLogin(Map<String, Object> params) {
		String userId = (String) params.get("userid");

		LoginService loginService = Context.get(LoginService.class);

		return loginService.login(userId);
	}

	@Override
	public ServiceResponse doScore(Map<String, Object> params) {
		System.out.println(
				"New request received to save the score " + (String) params.get(Constants.POST_BODY_PARAMETER_NAME));

		ScoreService scoreService = Context.get(ScoreService.class);

		return scoreService.addScore((String) params.get("sessionkey"), (String) params.get("levelid"),
				(String) params.get(Constants.POST_BODY_PARAMETER_NAME));
	}

	@Override
	public ServiceResponse doHighscoreList(Map<String, Object> params) {

		System.out.println(
				"Received a new request for the highest scores for the level " + (String) params.get("levelid"));

		HighscoreService highscoreService = Context.get(HighscoreService.class);
		return highscoreService.getHighscoreForLevel((String) params.get("levelid"));
	}

}
