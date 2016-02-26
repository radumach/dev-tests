package com.scoreServer.service.service.impl;

import static com.scoreServer.server.Constants.SESSION_MAP;
import static com.scoreServer.server.Constants.SESSION_STORE;

import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.bean.Session;
import com.scoreServer.server.util.SessionUtil;
import com.scoreServer.service.service.LoginService;

public class LoginServiceImpl implements LoginService {

	@Override
	public ServiceResponse login(String userIdString) {

		Integer userId = null;

		try {
			userId = Integer.parseUnsignedInt(userIdString);
		} catch (NumberFormatException e) {
			return new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY_422);
		}

		String sessionId = SessionUtil.getSessionId();

		Session session = new Session(sessionId);
		
		SESSION_STORE.put(sessionId, session);

		SESSION_MAP.put(session.getSessionId(), userId);

		return new ServiceResponse(sessionId);
	}

}
