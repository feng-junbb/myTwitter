package com.myTwitter.model;

public class Message {
	
	private Long id;
	private Long userId;
	private String message;
	
	/**
	 * default constructor
	 */
	public Message () {
		
	}
	
	public Message (Long userId, String message) {
		this.userId = userId;
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", userId=" + userId + ", message=" + message + "]";
	}
}
