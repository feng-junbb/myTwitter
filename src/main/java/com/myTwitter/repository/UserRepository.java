package com.myTwitter.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.myTwitter.model.Follower;
import com.myTwitter.model.Role;
import com.myTwitter.model.User;
import com.myTwitter.utils.JSONUtils;

@Repository
public class UserRepository {
	private static final String ROLES = "roles";
	private static final String SELECT_USER_FOLLOWING_FOLLOWER_SQL ="select * from user where id in "
			+ "(select follower_id as id from user_follower where user_id = ? "
			+ "union select user_id as id from user_follower where follower_id = ?)";
	private static final String DELETE_USER_FOLLOWER_SQL = "delete from user_follower where user_id = ? and follower_id = ?";
	private static final String SELECT_USER_FOLLOWER_SQL = "select * from user_follower where user_id = ? and follower_id = ?";
	private static final String INSERT_USER_FOLLOWER_SQL = "insert into user_follower (user_id, follower_id) values(?, ?)";
	private static final String SELECT_USER_ROLE_SQL = "select * from user_role where user_id = ? and role_id = ?";
	private static final String INSERT_USER_ROLE_SQL = "insert into user_role (user_id, role_id) values(?, ?)";
	private static final String SELECT_USER_BY_ID = "select * from user where id=?";
	private static final String SELECT_USER_BY_EMAIL_SQL = "select * from user where email=?";
	private static final String UPDATE_USER_SQL = "update user set firstName = ?, lastName = ?, email = ?, password = ? where id = ?";
	private static final String DELETE_USER_SQL = "delete from user where id = ?";
	private static final String SELECT_ALL_USER_SQL = "select * from user";
	private final static String INSERT_USER_SQL = "insert into user (id, firstName, lastName, email, password) values(?, ?, ?, ?, ?)";
	private static final String SEELEC_USER_AND_MOST_POPULAR_FOLLOWER_SQL = "SELECT t2.user_id,"
			+ "(SELECT t1.follower_id FROM user_follower t1 WHERE t1.user_id=t2.user_id "
			+ "GROUP BY t1.follower_id ORDER BY COUNT(t1.follower_id) DESC LIMIT 1) AS follower_id "
			+ "FROM user_follower t2 GROUP BY t2.user_id";
	private static Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	RoleRepository roleRepository;
	
	public boolean checkExistigByEmail(String email) {
		List<Map<String, Object>>rows = jdbcTemplate.queryForList(SELECT_USER_BY_EMAIL_SQL, new Object[] {
	            email
	        });
		if (rows.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkExistigById(long id) {
		List<Map<String, Object>>rows = jdbcTemplate.queryForList(SELECT_USER_BY_ID, new Object[] {
	            id
	        });
		if (rows.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public User findByEmail(String email) {
		if (checkExistigByEmail(email)) {
			User user = jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_SQL, new Object[] {
		            email
		        },
		        new BeanPropertyRowMapper < User > (User.class));
			List<Role> roles = roleRepository.findRolesForUser(user);
			user.setRoles(roles);
		    return user;
		} else {
			return null;
		}
	}
	
	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		User user = jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new Object[] {
	            id
	        },
	        new BeanPropertyRowMapper < User > (User.class));
		List<Role> roles = roleRepository.findRolesForUser(user);
		user.setRoles(roles);
	    return user;
	}
	
	/**
	* RowMapper that maps data from the user SQL table into User objects.
	*/
	class UserRowMapper implements RowMapper < User > {
		@Override
	    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		User user = new User();
	        user.setId(rs.getLong(DataBaseFieldName.ID));
	        user.setFirstName(rs.getString(DataBaseFieldName.FIRST_NAME));
	        user.setLastName(rs.getString(DataBaseFieldName.LAST_NAME));
	        user.setEmail(rs.getString(DataBaseFieldName.EMAIL));
	        user.setPassword(rs.getString(DataBaseFieldName.PASSWORD));
	        List<Role> roles = roleRepository.findRolesForUser(user);
			user.setRoles(roles);
	        return user;
	    }
	}
	
	public List < User > findAll() {
	    return jdbcTemplate.query(SELECT_ALL_USER_SQL, new UserRowMapper());
	}
	
	public int deleteById(long id) {
	    return jdbcTemplate.update(DELETE_USER_SQL, new Object[] {
	        id
	    });
	}
	public int insert(User user) {
	    return jdbcTemplate.update(INSERT_USER_SQL,
	        new Object[] {
	            user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()
	        });
	}
	public int update(User user) {
	    return jdbcTemplate.update(UPDATE_USER_SQL,
	        new Object[] {
	        		user.getFirstName(),user.getLastName(),user.getEmail(), user.getPassword(), user.getId()
	        });
	}
	
	public User create(User user) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user.getId());
				ps.setString(2, user.getFirstName());
				ps.setString(3, user.getLastName());
				ps.setString(4, user.getEmail());
				ps.setString(5, user.getPassword());
				return ps;
			}
		}, holder);

		if (holder.getKey() != null) {
			Long newUserId = holder.getKey().longValue();
			user.setId(newUserId);
		}
		
		return user;
	}
	
	public boolean checkRecordExisting(Long id1, Long id2, String sql) {
		List<Map<String, Object>>rows = jdbcTemplate.queryForList(sql, new Object[] {
	            id1, id2
	        });
		if (rows.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public User save(User user ) {
//		User userWithId = create(user);
		int count = 0;
		if (checkExistigById(user.getId())) {
			count = update(user);
		} else {
			count = insert(user);
		}
		if (count > 0) {
			for (Role role : user.getRoles()) {
				if (!checkRecordExisting(user.getId(), role.getId(), SELECT_USER_ROLE_SQL)) {
					jdbcTemplate.update(INSERT_USER_ROLE_SQL, 
							new Object[] {
									user.getId(), role.getId()
				        });
				}
			}
		}
		return user;
	}
	
	public User save(String jsonString) {
		JSONObject jObject = JSONUtils.convertStringToJSON(jsonString);
		if (jObject.isNull(DataBaseFieldName.ID) 
				|| jObject.isNull(DataBaseFieldName.FIRST_NAME)
				|| jObject.isNull(DataBaseFieldName.LAST_NAME)
				|| jObject.isNull(DataBaseFieldName.EMAIL)
				|| jObject.isNull(DataBaseFieldName.PASSWORD)) {
			return null;
		}
		User user = new User();
		user.setId(jObject.getLong(DataBaseFieldName.ID));
		user.setFirstName(jObject.getString(DataBaseFieldName.FIRST_NAME));
		user.setLastName(jObject.getString(DataBaseFieldName.LAST_NAME));
		user.setEmail(jObject.getString(DataBaseFieldName.EMAIL));
		user.setPassword(jObject.getString(DataBaseFieldName.PASSWORD));
//		user.setPassword(passwordEncoder.encode(jObject.getString(DataBaseFieldName.PASSWORD)));
		JSONArray jRoles = jObject.getJSONArray(ROLES);
		List<Role> roles = new ArrayList<Role>();
		for (int i = 0; i < jRoles.length(); i++) {
			Role role = new Role();
			role.setId(jRoles.getJSONObject(i).getLong(DataBaseFieldName.ID));
			role.setName(jRoles.getJSONObject(i).getString(DataBaseFieldName.NAME));
			roles.add(role);
		}
		user.setRoles(roles);
		return save(user);		
	}
	
	public boolean follow(String jsonString ) {
		JSONObject jObject = JSONUtils.convertStringToJSON(jsonString);
		if (jObject.isNull(DataBaseFieldName.USER_ID) || jObject.isNull(DataBaseFieldName.FOLLOWER_ID)) {
			return false;
		}
		Long userId = jObject.getLong(DataBaseFieldName.USER_ID);
		Long followingId = jObject.getLong(DataBaseFieldName.FOLLOWER_ID);
		if (!checkRecordExisting(userId, followingId, SELECT_USER_FOLLOWER_SQL)) {
			jdbcTemplate.update(INSERT_USER_FOLLOWER_SQL, 
					new Object[] {
							userId, followingId
		        });
		}
		return true;
	}
	
	public boolean deleteFollowing(String jsonString ) {
		JSONObject jObject = JSONUtils.convertStringToJSON(jsonString);
		if (jObject.isNull(DataBaseFieldName.USER_ID) || jObject.isNull(DataBaseFieldName.FOLLOWER_ID)) {
			return false;
		}
		Long userId = jObject.getLong(DataBaseFieldName.USER_ID);
		Long followingId = jObject.getLong(DataBaseFieldName.FOLLOWER_ID);
		jdbcTemplate.update(DELETE_USER_FOLLOWER_SQL, 
				new Object[] {
						userId, followingId
	        });
		return true;
	}
	
	public List<User> getUserFollowingFollowerByUserId(Long id) {
		return jdbcTemplate.query(SELECT_USER_FOLLOWING_FOLLOWER_SQL, new Object[] {
	            id, id
	        }, new UserRowMapper());
	}
	
	/**
	* RowMapper that maps data from the user_follower SQL table into Follower objects.
	*/
	class FollowerMapper implements RowMapper < Follower > {
		@Override
	    public Follower mapRow(ResultSet rs, int rowNum) throws SQLException {
			Follower follower = new Follower();
	        follower.setUserId(rs.getLong(DataBaseFieldName.USER_ID));
	        follower.setFollowerId(rs.getInt(DataBaseFieldName.FOLLOWER_ID));
	        return follower;
	    }
	}
	
	public List<Follower> getUsersWithMostPopularFollowers() {
		return jdbcTemplate.query(SEELEC_USER_AND_MOST_POPULAR_FOLLOWER_SQL, new FollowerMapper());
	}
	
}
