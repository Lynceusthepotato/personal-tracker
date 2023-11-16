package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class TransactionRepositoryImplement implements TransactionRepository{

    private static final String SQL_FINANCE_SEARCH_SUB_QUERY = "(SELECT finance_id from finance_list fl where fl.user_id = ?)";
    private static final String SQL_CREATE_TRANSACTION = "INSERT INTO transaction_list (finance_id, transaction_numeral, transaction_description, transaction_date) VALUES (" + SQL_FINANCE_SEARCH_SUB_QUERY + ", ?, ?, ?)";
    private static final String SQL_FIND_TRANSACTION_BY_ID = "SELECT * FROM transaction_list WHERE transaction_id = ? AND finance_id = " + SQL_FINANCE_SEARCH_SUB_QUERY + " ";
    private static final String SQL_FIND_ALL_TRANSACTION = "SELECT * FROM transaction_list WHERE finance_id = " + SQL_FINANCE_SEARCH_SUB_QUERY + " ";
    private static final String SQL_UPDATE_TRANSACTION = "UPDATE transaction_list SET transaction_numeral = ?, transaction_description = ?, transaction_date = ? WHERE transaction_id = ? AND finance_id = " + SQL_FINANCE_SEARCH_SUB_QUERY + " ";
    private static final String SQL_DELETE_TRANSACTION = "DELETE FROM transaction_list WHERE transaction_id = ? AND finance_id = " + SQL_FINANCE_SEARCH_SUB_QUERY + " ";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Long userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_TRANSACTION, transactionRowMapper, userId);
    }

    @Override
    public Transaction findById(Long userId, Long transactionId) throws JSResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_TRANSACTION_BY_ID, transactionRowMapper, transactionId, userId);
        } catch (Exception e) {
            throw new JSResourceNotFoundException("Transaction not found" + e);
        }
    }

    @Override
    public Long create(Long userId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setInt(2, transactionNumeral);
                ps.setString(3, transactionDescription);
                ps.setObject(4, transactionDate);
                return ps;
            }, keyHolder);
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("transaction_id");
        } catch (Exception e) {
            throw new JSBadRequestException("Unable to create transaction" + e);
        }
    }

    @Override
    public void update(Long userId, Long transactionId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException {
        try {
            Date sqlDate = Date.valueOf(transactionDate);
            jdbcTemplate.update(SQL_UPDATE_TRANSACTION, transactionNumeral, transactionDescription, sqlDate, transactionId, userId);
        } catch (Exception e) {
            throw new JSBadRequestException("Unable to update transaction" + e);
        }
    }

    @Override
    public void removeById(Long userId, Long transactionId) throws JSResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE_TRANSACTION, transactionId, userId);
        if (count == 0) {
            throw new JSResourceNotFoundException("Transaction not found");
        }
    }

    private final RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        return new Transaction(
                rs.getLong("transaction_id"),
                rs.getLong("finance_id"),
                rs.getInt("transaction_numeral"),
                rs.getString("transaction_description"),
                rs.getDate("transaction_date").toLocalDate()
        );
    });
}
