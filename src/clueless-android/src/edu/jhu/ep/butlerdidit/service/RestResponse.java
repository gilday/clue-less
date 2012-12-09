package edu.jhu.ep.butlerdidit.service;

class RestResponse {
	
	private int httpStatusCode;
	private String jsonString = null;
	
	public RestResponse(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public RestResponse(int httpStatusCode, String jsonString) {
		this.httpStatusCode = httpStatusCode;
		this.jsonString = jsonString;
	}
	
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	
	public String getJsonString() {
		return jsonString;
	}
}
