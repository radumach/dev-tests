package com.scoreServer.server.bean;

import com.scoreServer.server.Constants.HttpStatus;

//TODO maybe make immutable and refactor as necesasry in other code
public class ServiceResponse {
	
	private String response;
	private HttpStatus status = HttpStatus.OK_200;
	
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

	public void setResponse(String response) {
		this.response = response;
	}

	public int getStatus() {
		return status.getCode();
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public boolean isError() {
		return this.status.getCode() < 200 || this.status.getCode() >= 300;
	}
	
	@Override
	public String toString() {
		return response + " " + status;
	}

}
