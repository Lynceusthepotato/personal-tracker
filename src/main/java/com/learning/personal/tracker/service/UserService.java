package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSAuthException;
import com.learning.personal.tracker.model.User;

public interface UserService {
    User requestLogin(String email, String password) throws JSAuthException;
    User registerUser(String email, String username, String password) throws JSAuthException;
}
