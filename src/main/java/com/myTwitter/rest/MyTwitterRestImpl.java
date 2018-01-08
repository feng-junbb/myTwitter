package com.myTwitter.rest;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myTwitter.model.Follower;
import com.myTwitter.model.Message;
import com.myTwitter.model.User;
import com.myTwitter.repository.MessageRepository;
import com.myTwitter.repository.UserRepository;

@Component
public class MyTwitterRestImpl {
	private static final String MESSAGES = "messages";

	private static final String USERS = "users";

	private static Logger logger = LoggerFactory.getLogger(MyTwitterRestImpl.class);
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private MessageRepository messageRepository;
	
	public RestResponse getUsers() {
		List<User> users = userRepository.findAll();
		HashMap<String, Object> returnData = new HashMap<String, Object>();
		returnData.put(USERS, users);
		return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
	}
	
	public RestResponse getUserById(Long id) {
		User users = userRepository.findById(id);
		if (users != null) {
			HashMap<String, Object> returnData = new HashMap<String, Object>();
			returnData.put(USERS, users);
			return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
		} else {
			return new RestResponse(RestResponse.STATUS_FAIL);
		}
	}
	
	public RestResponse saveUser(String requestJSONString) {
		User users = userRepository.save(requestJSONString);
		if (users != null) {
			HashMap<String, Object> returnData = new HashMap<String, Object>();
			returnData.put(USERS, users);
			return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
		} else {
			return new RestResponse(RestResponse.STATUS_FAIL);
		}
	}
	
	public RestResponse following(String requestJSONString) {
		if (userRepository.follow(requestJSONString)) {
			return new RestResponse(RestResponse.STATUS_SUCCESS);
		} else {
			return new RestResponse(RestResponse.STATUS_FAIL);
		}
	}
	
	public RestResponse deleteFollowing(String requestJSONString) {
		if (userRepository.deleteFollowing(requestJSONString)) {
			return new RestResponse(RestResponse.STATUS_SUCCESS);
		} else {
			return new RestResponse(RestResponse.STATUS_FAIL);
		}
	}
	
	public RestResponse followings(Long id) {
		List<User> users = userRepository.getUserFollowingFollowerByUserId(id);
		HashMap<String, Object> returnData = new HashMap<String, Object>();
		returnData.put(USERS, users);
		return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
	}
	
	public RestResponse getUsersWithMostPopularFollowers() {
		List<Follower> followers = userRepository.getUsersWithMostPopularFollowers();
		HashMap<String, Object> returnData = new HashMap<String, Object>();
		returnData.put(USERS, followers);
		return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
	}
	
	public RestResponse createMessage(String requestJSONString) {
		if (messageRepository.save(requestJSONString)) {
			return new RestResponse(RestResponse.STATUS_SUCCESS);
		} else {
			return new RestResponse(RestResponse.STATUS_FAIL);
		}
	}
	
	public RestResponse getUserAndFollwingMessage(Long id, String search) {
		List<Message> messages = messageRepository.findMessagesForUserAndFollwing(id, search);
		HashMap<String, Object> returnData = new HashMap<String, Object>();
		returnData.put(MESSAGES, messages);
		return new RestResponse(RestResponse.STATUS_SUCCESS, returnData);
	}
	
}
