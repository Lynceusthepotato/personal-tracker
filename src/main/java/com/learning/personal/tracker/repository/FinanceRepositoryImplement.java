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

    private static final String SQL_CREATE_FINANCE = "INSERT INTO finance_list (user_id, finance_budget, finance_monthly_budget, do_warn) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_FINANCE_BY_ID = "SELECT * FROM finance_list fl WHERE fl.user_id = ?";
    private static final String SQL_UPDATE_FINANCE = "UPDATE finance_list SET finance_budget = ?, finance_monthly_budget = ?, do_warn = ? WHERE user_id = ? AND finance_id = ?";
    private static final String SQL_DELETE_FINANCE = "DELETE FROM finance_list where user_id = ?";
    private static final String SQL_DELETE_ALL_TRANSACTION = "DELETE FROM transaction_list where finance_id = (SELECT finance_id from finance_list fl where fl.user_id = ?)";
    private static final String SQL_UPDATE_FINANCE_CURRENT_BUDGET = "UPDATE finance_list SET finance_budget = finance_budget - ? WHERE user_id = ?";
    private static final String SQL_RESET_FINANCE_BUDGET = "UPDATE finance_list SET finance_budget = finance_monthly_budget";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Finance findById(Long userId) throws JSResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_FINANCE_BY_ID, financeRowMapper, userId);
        } catch (Exception e) {
            throw new JSResourceNotFoundException("Data not found");
        }
    }

    @Override
    public Long create(Long userId, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE_FINANCE, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setDouble(2, financeMonthlyBudget);
                ps.setDouble(3, financeMonthlyBudget);
                ps.setBoolean(4, doWarn);
                return ps;
            }, keyHolder);
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("finance_id");
        } catch (Exception e) {
            throw new JSBadRequestException("Invalid Request" + e);
        }
    }

    @Override
    public void update(Long userId, Long finance_id, Double financeBudget, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_FINANCE, financeMonthlyBudget, financeMonthlyBudget, doWarn, userId, finance_id);
        } catch (Exception e) {
            throw new JSBadRequestException("Invalid Request");
        }
    }

    @Override
    public void updateCurrentBudget(Long userId, Double financeBudget) throws JSBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE_FINANCE_CURRENT_BUDGET, financeBudget, userId);
        } catch (Exception e) {
            throw new JSBadRequestException("Invalid Request");
        }
    }

    @Override
    public void resetMonthlyBudget() {
        jdbcTemplate.update(SQL_RESET_FINANCE_BUDGET);
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
                rs.getDouble("finance_budget"),
                rs.getDouble("finance_monthly_budget"),
                rs.getBoolean("do_warn")
        );
    });
}
