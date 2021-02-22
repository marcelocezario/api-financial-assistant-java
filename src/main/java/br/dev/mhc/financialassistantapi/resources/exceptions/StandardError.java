package br.dev.mhc.financialassistantapi.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String message;
	private Instant timeStamp;

	public StandardError(Integer status, String message, Instant timeStamp) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public StandardError(Integer status, String message, Long timeMilliSeconds) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = Instant.ofEpochMilli(timeMilliSeconds);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setTimeStamp(Long timeMilliSeconds) {
		this.timeStamp = Instant.ofEpochMilli(timeMilliSeconds);
	}
}
