package com.scoreServer.server.bean;

import com.scoreServer.server.Constants.HttpStatus;

public class ServiceResponse {
	
	private final String response;
	private final HttpStatus status;
	
	public ServiceResponse() {
		this("", HttpStatus.OK_200);
	}
	
	public ServiceResponse(String response) {
		this(response, HttpStatus.OK_200);
	}
	
	public ServiceResponse(String response, HttpStatus status) {
		this.response = response;
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public int getStatus() {
		return status.getCode();
	}

	public boolean isError() {
		return this.status.getCode() < 200 || this.status.getCode() >= 300;
	}
	
	@Override
	public String toString() {
		return response + " " + status;
	}

}
