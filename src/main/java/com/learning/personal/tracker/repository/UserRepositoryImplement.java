package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSAuthException;
import com.learning.personal.tracker.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Objects;


@Repository
public class UserRepositoryImplement implements UserRepository {
    private static final String SQL_CREATE = "INSERT INTO user_list(email, username, password, last_login, created_at) VALUES(?, ?, ?, current_timestamp, current_timestamp)";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM user_list WHERE email = ?";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM user_list WHERE email = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM user_list WHERE user_id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Long create(String email, String username, String password) throws JSAuthException {
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, username);
                ps.setString(3, hashPassword);
                return ps;
            }, keyHolder);
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("user_id");
        } catch (Exception e) {
            throw new JSAuthException("Failed to create account :(");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws JSAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, email);
            if (user != null) {
                if (!BCrypt.checkpw(password, user.getPassword())) {
                    throw new JSAuthException("Invalid email or password");
                }
                return user;
            } else {
                throw new JSAuthException("Invalid email or password");
            }
        } catch (Exception e) {
            throw new JSAuthException("Invalid email or password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, email);
    }

    @Override
    public User findById(Long userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, userId);
    }

    private final RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        LocalDateTime lastLogin = rs.getObject("last_login", LocalDateTime.class);
        LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
        return new User(
                rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                lastLogin, createdAt);
    });
}
