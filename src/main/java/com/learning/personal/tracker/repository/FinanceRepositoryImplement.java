package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Finance;
import com.learning.personal.tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class FinanceRepositoryImplement implements FinanceRepository{

    private static final String SQL_CREATE_FINANCE = "INSERT INTO finance_list (user_id, finance_budget, do_warn) VALUES (?, ?, ?)";
    private static final String SQL_FIND_FINANCE_BY_ID = "SELECT * FROM finance_list fl WHERE fl.user_id = ?";
    private static final String SQL_UPDATE_FINANCE = "UPDATE finance_list SET finance_budget = ?, do_warn = ? WHERE user_id = ? AND finance_id = ?";
    private static final String SQL_DELETE_FINANCE = "DELETE FROM finance_list where user_id = ?";
    private static final String SQL_DELETE_ALL_TRANSACTION = "DELETE FROM transaction_list where finance_id = (SELECT finance_id from finance_list fl where fl.user_id = ?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Finance findById(Long userId) throws JSResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_FINANCE_BY_ID, financeRowMapper, userId);
        } catch (Exception e) {
            throw new JSResourceNotFoundException("Data not found" + e);
        }
    }

    @Override
    public Long create(Long userId, Integer financeBudget, boolean doWarn) throws JSBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE_FINANCE, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setInt(2, financeBudget);
                ps.setBoolean(3, doWarn);
                return ps;
            }, keyHolder);
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("finance_id");
        } catch (Exception e) {
            throw new JSBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Long userId, Long finance_id, Integer financeBudget, boolean doWarn) throws JSBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_FINANCE, financeBudget, doWarn, userId, finance_id);
        } catch (Exception e) {
            throw new JSBadRequestException("Invalid Request");
        }
    }

    @Override
    public void removeById(Long userId) {
        this.removeAllTransaction(userId);
        jdbcTemplate.update(SQL_DELETE_FINANCE, userId);
    }

    private void removeAllTransaction(Long userId) {
        jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTION, userId);
    }

    private final RowMapper<Finance> financeRowMapper = ((rs, rowNum) -> {
        return new Finance(
                rs.getLong("finance_id"),
                rs.getLong("user_id"),
                rs.getInt("finance_budget"),
                rs.getBoolean("do_warn")
        );
    });
}
