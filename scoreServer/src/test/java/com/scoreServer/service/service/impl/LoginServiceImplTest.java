package com.scoreServer.service.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.scoreServer.server.Constants.HttpStatus;
import com.scoreServer.server.bean.ServiceResponse;
import com.scoreServer.server.testUtils.SessionDataMockHelper;

public class LoginServiceImplTest {

	private LoginServiceImpl loginServiceImpl;
	
	@Before
	public void init() {
		loginServiceImpl = new LoginServiceImpl();
		SessionDataMockHelper.clearAll();
	}
	
	@Test
	public void testLoginNewUser() {
		ServiceResponse serviceResponse1 = loginServiceImpl.login("1");
		assertEquals(200, serviceResponse1.getStatus());
		assertTrue(serviceResponse1.getResponse().trim().length() > 0);
		
		ServiceResponse serviceResponse2 = loginServiceImpl.login("2");
		assertEquals(200, serviceResponse2.getStatus());
		assertTrue(serviceResponse2.getResponse().trim().length() > 0);
		
		assertFalse(serviceResponse1.getResponse().equals(serviceResponse2.getResponse()));
	}
	
	@Test
	public void testRelogin() {
		ServiceResponse serviceResponse1 = loginServiceImpl.login("1");
		assertEquals(200, serviceResponse1.getStatus());
		assertTrue(serviceResponse1.getResponse().trim().length() > 0);
		
		ServiceResponse serviceResponse2 = loginServiceImpl.login("1");
		assertEquals(200, serviceResponse2.getStatus());
		assertTrue(serviceResponse2.getResponse().trim().length() > 0);
		
		assertFalse(serviceResponse1.getResponse().equals(serviceResponse2.getResponse()));
	}
	
	@Test
	public void testInvalidUserId() {
		ServiceResponse serviceResponse1 = loginServiceImpl.login("abc");
		assertEquals(422, serviceResponse1.getStatus());
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422.getMessage(), serviceResponse1.getResponse());
		assertTrue(serviceResponse1.isError());
	}
}
