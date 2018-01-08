package com.myTwitter.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class RestResponse {
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";

	private String status;
	private String message = "";
	private HttpStatus error_code = HttpStatus.ACCEPTED;
	private Map<String, Object> data;


	/**
	 * @param status
	 */
	public RestResponse() {
	}
	
	/**
	 * @param status
	 */
	public RestResponse(String status) {
		this.status = status;
	}

	/**
	 * @param status
	 * @param data
	 */
	public RestResponse(String status, Map<String, Object> data) {
		this.status = status;
		this.data = data;
	}

	/**
	 * @param status
	 * @param message
	 * @param error_code
	 */
	public RestResponse(String status, String message, HttpStatus error_code) {
		this.status = status;
		this.message = message;
		this.error_code = error_code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the error_code
	 */
	public HttpStatus getError_code() {
		return error_code;
	}

	/**
	 * @param error_code
	 *            the error_code to set
	 */
	public void setError_code(HttpStatus error_code) {
		this.error_code = error_code;
	}

	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}