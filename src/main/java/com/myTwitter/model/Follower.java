package com.myTwitter.model;

public class Follower {
	private long userId; // ID of the user
	private long followerId; // ID of the follower
	
	/**
	 * default constructor
	 */
	public Follower() {
		
	}
	/**
	 * @param user_id
	 * @param follower_id
	 */
	public Follower(long userId, long followerId) {
		super();
		this.userId = userId;
		this.followerId = followerId;
	}
	/**
	 * @return the user_id
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the follower_id
	 */
	public long getFollowerId() {
		return followerId;
	}
	/**
	 * @param follower_id the follower_id to set
	 */
	public void setFollowerId(long followerId) {
		this.followerId = followerId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Followers [userId=" + userId + ", followerId=" + followerId + "]";
	}
}
