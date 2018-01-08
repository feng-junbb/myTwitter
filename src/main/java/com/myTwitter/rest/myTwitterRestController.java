package com.myTwitter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class myTwitterRestController {
	private static Logger logger = LoggerFactory.getLogger(myTwitterRestController.class);
	
	@Autowired
	private MyTwitterRestImpl restServiceImpl;
	
	/**
	 * list all users
	 * @return
	 */
	@RequestMapping(value = "/api/v0/Users", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> getUsers() {
		logger.debug("getUsers");
		RestResponse response = restServiceImpl.getUsers();
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * create new user
	 * @param requestJSONString
	 * @return
	 */
	@RequestMapping(value = "/api/v0/Users", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> saveUser(@RequestBody String requestJSONString) {
		logger.debug("saveUsers");
		RestResponse response = restServiceImpl.saveUser(requestJSONString);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * get user by Id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/v0/Users/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> getUserById(@PathVariable("id") Long id) {
		logger.debug("getUserById");
		RestResponse response = restServiceImpl.getUserById(id);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * follow an user
	 * @param requestJSONString
	 * @return
	 */
	@RequestMapping(value = "/api/v0/following", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> following(@RequestBody String requestJSONString) {
		logger.debug("following");
		RestResponse response = restServiceImpl.following(requestJSONString);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * unfollow an user
	 * @param requestJSONString
	 * @return
	 */
	@RequestMapping(value = "/api/v0/following", method = RequestMethod.DELETE, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> deleteFollowing(@RequestBody String requestJSONString) {
		logger.debug("deleteFollowing");
		RestResponse response = restServiceImpl.deleteFollowing(requestJSONString);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * get the list of people the user is following as well as the followers of the user.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/v0/followings/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> followings(@PathVariable("id") Long id) {
		logger.debug("followings");
		RestResponse response = restServiceImpl.followings(id);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * create new message
	 * @param requestJSONString
	 * @return
	 */
	@RequestMapping(value = "/api/v0/messages", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> createMessage(@RequestBody String requestJSONString) {
		logger.debug("saveMessage");
		RestResponse response = restServiceImpl.createMessage(requestJSONString);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * message list for the current user also include messages they have sent and messages sent by users 
	 * they follow. Support a “search=” parameter that can be used to further filter messages based on keyword
	 * @param id
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/api/v0/messages/{user_id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> getMessages(@PathVariable("user_id") Long id, String search) {
		logger.debug("getMessage");
		RestResponse response = restServiceImpl.getUserAndFollwingMessage(id, search);
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
	
	/**
	 * list of all users id and most popular follower user id
	 * @return
	 */
	@RequestMapping(value = "/api/v0/users_popular_followers", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	ResponseEntity<RestResponse> getUsersWithMostPopularFollowers() {
		logger.debug("getUsersWithMostPopularFollowers");
		RestResponse response = restServiceImpl.getUsersWithMostPopularFollowers();
		return new ResponseEntity<RestResponse>(response, response.getError_code());
	}
}
