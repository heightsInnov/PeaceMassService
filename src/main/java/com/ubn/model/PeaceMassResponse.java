package com.ubn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeaceMassResponse{

	@JsonProperty("response_code")
	String responseCode;
	@JsonProperty("success")
	boolean success;
	@JsonProperty("message")
	String message;
	@JsonProperty("payload")
	List<Payload> payload;
	
	public PeaceMassResponse() {
		
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Payload> getPayload() {
		return payload;
	}
	public void setPayload(List<Payload> payload) {
		this.payload = payload;
	}
	public PeaceMassResponse(String responseCode, boolean success, String message, List<Payload> payload) {
		super();
		this.success = success;
		this.message = message;
		this.payload = payload;
		this.responseCode = responseCode;
	}
}
