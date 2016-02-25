package com.scoreServer.service.service;

import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.service.framework.annotation.Implementor;
import com.scoreServer.service.service.impl.LoginServiceImpl;

@Implementor(LoginServiceImpl.class)
public interface LoginService {
	
	ServiceResponse login(String userId);

}
