package com.scoreServer.service.service.impl;

import static com.scoreServer.server.Constants.SESSION_MAP;
import static com.scoreServer.server.Constants.SESSION_STORE;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.bean.Session;
import com.scoreServer.server.util.SessionUtil;
import com.scoreServer.service.service.LoginService;

public class LoginServiceImpl implements LoginService {

	@Override
	public ServiceResponse login(String userIdString) {

		Integer userId = Integer.parseInt(userIdString);

		String sessionId = SessionUtil.getSessionId();

		Session session = new Session(sessionId);

		if (!SESSION_STORE.keySet().contains(sessionId)) {
			return login(userIdString);
		}
		
		SESSION_STORE.put(sessionId, session);

		SESSION_MAP.put(session.getSessionId(), userId);

		return new ServiceResponse(sessionId);
	}

}
