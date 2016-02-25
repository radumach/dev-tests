package com.scoreServer.server.bean;

public class ServiceResponse {

	private String response;
	private int status = 200;
	private boolean isError = false;

	public ServiceResponse(String response) {
		this(response, 200);
		
	}
	
	public ServiceResponse(String response, int status) {
		this.response = response;
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

}
