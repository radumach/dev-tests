package com.scoreServer.server.testUtils;

import org.mockito.Mockito;

import com.scoreServer.service.framework.Context;

public class ServiceLocatorMockHelper {

	public static <T> T mockAndRegister(Class<T> type) {
		T mockedInstance = Mockito.mock(type);
		Context.set(type, mockedInstance);
		return mockedInstance;
	}
	
	public static void clearRegistredInstances() {
		Context.clear();
	}
}
