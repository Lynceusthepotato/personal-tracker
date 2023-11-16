package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSAuthException;
import com.learning.personal.tracker.model.User;

public interface UserRepository{
    Long create(String email, String username, String Password) throws JSAuthException;
    User findByEmailAndPassword(String email, String password) throws JSAuthException;
    Integer getCountByEmail(String email);
    User findById(Long userId);
}
