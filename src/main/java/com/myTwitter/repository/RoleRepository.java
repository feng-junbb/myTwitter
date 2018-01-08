package com.myTwitter.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.myTwitter.model.Role;
import com.myTwitter.model.User;

@Repository
public class RoleRepository {
	private static final String DELETE_ROLE_SQL = "delete from role where id=?";
	private static final String SELECT_ALL_ROLE_SQL = "select * from role";
	private static final String SELECT_ROLE_SQL = "select * from role where id=?";
	private static final String UPDATE_ROLE_SQL = "update role set name = ? where id = ?";
	private static final String INSERT_ROLE_SQL = "insert into role (id, name) values(?, ?)";
	private static final String SEELEC_USER_ROLES_SQL = "select * from role where id in (select role_id from user_role where user_id = ?)";

	private static Logger logger = LoggerFactory.getLogger(RoleRepository.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	class RoleRowMapper implements RowMapper < Role > {

		@Override
	    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		Role role = new Role();
	        role.setId(rs.getLong(DataBaseFieldName.ID));
	        role.setName(rs.getString(DataBaseFieldName.NAME));
	        return role;
	    }
	}
	
	public Role findById(String id) {
	    return jdbcTemplate.queryForObject(SELECT_ROLE_SQL, new Object[] {
	            id
	        },
	        new BeanPropertyRowMapper < Role > (Role.class));
	}
	
	public Role findByName(String name) {
	    return jdbcTemplate.queryForObject(SELECT_ROLE_SQL, new Object[] {
	            name
	        },
	        new RoleRowMapper());
	}
	
	public List<Role> findAll() {
	    return jdbcTemplate.query(SELECT_ALL_ROLE_SQL, new RoleRowMapper());
	}
	
	public List<Role> findRolesForUser(User user) {
		return jdbcTemplate.query(SEELEC_USER_ROLES_SQL, new Object[] {
	            user.getId()
	        },
	        new RoleRowMapper());
	}
	
	public int deleteById(long id) {
	    return jdbcTemplate.update(DELETE_ROLE_SQL, new Object[] {
	        id
	    });
	}
	public int insert(Role role) {
	    return jdbcTemplate.update(INSERT_ROLE_SQL,
	        new Object[] {
	        		role.getId(), role.getName()
	        });
	}
	public int update(Role role) {
	    return jdbcTemplate.update(UPDATE_ROLE_SQL,
	        new Object[] {
	            role.getName(), role.getId()
	        });
	}
	
	public void insertBatch(final List<Role> roles){
		jdbcTemplate.batchUpdate(INSERT_ROLE_SQL, new BatchPreparedStatementSetter() {

		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			Role role = roles.get(i);
			ps.setLong(1, role.getId());
			ps.setString(2, role.getName());
		}

		@Override
		public int getBatchSize() {
			return roles.size();
		}
		  });
	}
}
