package com.scoreServer.server.bean;

public class Session {
	
	private long timestamp;
	private String sessionId;
	
	public Session(String sessionId) {
		this.sessionId = sessionId;
		this.timestamp = System.currentTimeMillis();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
	
}
