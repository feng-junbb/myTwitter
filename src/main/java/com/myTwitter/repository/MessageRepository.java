package com.myTwitter.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.myTwitter.model.Message;
import com.myTwitter.model.User;
import com.myTwitter.utils.JSONUtils;

@Repository
public class MessageRepository { 
	private static final String DELETE_MESSAGE_SQL = "delete from message where id=?";
	private static final String SELECT_ALL_MESSAGE_SQL = "select * from message";
	private static final String SELECT_MESSAGE_SQL = "select * from message where id=?";
	private static final String INSERT_MESSAGE_SQL = "insert into message (user_id, message) values(?, ?)";
	private static final String SEELEC_USER_MESSAGE_SQL = "select * from message where user_id = ?";
	private static final String SEELEC_USER_AND_FOLLWOING_MESSAGE_SQL = "select * from message where user_id = ? "
			+ "union select * from message where user_id "
			+ "in (select follower_id from user_follower where user_id = ?)";
	
	private static Logger logger = LoggerFactory.getLogger(MessageRepository.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	class MessageRowMapper implements RowMapper < Message > {
		@Override
	    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		Message message = new Message();
	    		message.setId(rs.getLong(DataBaseFieldName.ID));
	    		message.setUserId(rs.getLong(DataBaseFieldName.USER_ID));
	    		message.setMessage(rs.getString(DataBaseFieldName.MESSAGE));
	        return message;
	    }
	}
	
	public Message findById(String id) {
	    return jdbcTemplate.queryForObject(SELECT_MESSAGE_SQL, new Object[] {
	            id
	        },
	        new BeanPropertyRowMapper < Message > (Message.class));
	}
	
	public List<Message> findMessagesForUser(User user) {
		return jdbcTemplate.query(SEELEC_USER_MESSAGE_SQL, new Object[] {
	            user.getId()
	        },
	        new MessageRowMapper());
	}
	
	public List<Message> findMessagesForUserAndFollwing(Long userId, String keyword) {
		String sql = SEELEC_USER_AND_FOLLWOING_MESSAGE_SQL;
//		somehow the LIKE does not work in H2		
//		if (!StringUtils.isEmpty(keyword)) {
//		sql = sql + " and message like '%" + keyword + "%'";
//			sql = sql + " and message regexp '.*"+keyword+ ".*'";
//		}
		
		List<Message> messages = jdbcTemplate.query(sql, new Object[] {
				userId, userId
        },
        new MessageRowMapper());
		
		// apply key words to filter the message in program, this you can provide multiple key words		
		if (messages != null && !messages.isEmpty() && !StringUtils.isEmpty(keyword)) {
			List<Message> selectedMessages = new ArrayList<Message>();
			for (Message message : messages) {
				if (message.getMessage().contains(keyword)) {
					selectedMessages.add(message);
				}
			}
			return selectedMessages;
		} else {
			return messages;
		}
	}
	
	public List<Message> findAll() {
	    return jdbcTemplate.query(SELECT_ALL_MESSAGE_SQL, new MessageRowMapper());
	}
	
	public int deleteById(long id) {
	    return jdbcTemplate.update(DELETE_MESSAGE_SQL, new Object[] {
	        id
	    });
	}
	public int insert(Message message) {
	    return jdbcTemplate.update(INSERT_MESSAGE_SQL,
	        new Object[] {
	        		message.getUserId(), message.getMessage()
	        });
	}
	
	public boolean save(String jsonString) {
		JSONObject jObject = JSONUtils.convertStringToJSON(jsonString);
		if (jObject.isNull(DataBaseFieldName.USER_ID)
				|| jObject.isNull(DataBaseFieldName.MESSAGE)) {
			return false;
		}
		Message message = new Message();
		message.setUserId(jObject.getLong(DataBaseFieldName.USER_ID));
		message.setMessage(jObject.getString(DataBaseFieldName.MESSAGE));
		return insert(message) > 0 ? true : false;		
	}
}
