package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.TransactionCategory;
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
public class TransactionCategoryRepositoryImplement implements TransactionCategoryRepository{

    private static final String SQL_CATEGORY_GET_ALL = "SELECT * FROM category_list";
    private static final String SQL_CATEGORY_FIND_BY_ID = "SELECT * FROM category_list WHERE category_id = ?";
    private static final String SQL_CATEGORY_CREATE = "INSERT INTO category_list (category_name) VALUES (?)";
    private static final String SQL_TRANSACTION_CATEGORY = "(SELECT category_id WHERE transaction_id = ?)";
    private static final String SQL_CATEGORY_FIND_BY_TRANSACTION_ID = "SELECT * FROM category_list WHERE category_id = " + SQL_TRANSACTION_CATEGORY + " ";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<TransactionCategory> findAll() {
        return jdbcTemplate.query(SQL_CATEGORY_GET_ALL, categoryRowMapper);
    }

    @Override
    public TransactionCategory findById(Long categoryId) throws JSResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_CATEGORY_FIND_BY_ID, categoryRowMapper, categoryId);
        } catch (Exception e) {
            throw new JSResourceNotFoundException("Category not found");
        }
    }

    @Override
    public TransactionCategory findByTransactionId(Long transactionId) throws JSResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_CATEGORY_FIND_BY_TRANSACTION_ID, categoryRowMapper, transactionId);
        } catch (Exception e) {
            throw new JSResourceNotFoundException("Category not found");
        }
    }

    @Override
    public Long create(String categoryName) throws JSBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_CATEGORY_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, categoryName);
                return ps;
            }, keyHolder);
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("transaction_id");
        } catch (Exception e) {
            throw new JSBadRequestException("Unable to create transaction" + e);
        }
    }
    private final RowMapper<TransactionCategory> categoryRowMapper = ((rs, rowNum) -> {
        return new TransactionCategory(
                rs.getLong("category_id"),
                rs.getString("category_name")
        );
    });
}
