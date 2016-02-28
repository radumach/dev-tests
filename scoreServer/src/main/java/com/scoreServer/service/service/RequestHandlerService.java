package com.scoreServer.service.service;

import java.util.Map;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.RequestHandlerServiceImpl;

@Implementor(RequestHandlerServiceImpl.class)
public interface RequestHandlerService {
	
	ServiceResponse doLogin(Map<String, Object> params);
	ServiceResponse doScore(Map<String, Object> params);
	ServiceResponse doHighscoreList(Map<String, Object> params);

}
