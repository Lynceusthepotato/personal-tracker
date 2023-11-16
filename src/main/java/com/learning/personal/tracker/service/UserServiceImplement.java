package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSAuthException;
import com.learning.personal.tracker.model.User;
import com.learning.personal.tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImplement implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User requestLogin(String email, String password) throws JSAuthException {
        if (email != null) {
            String emailPreview = email.toLowerCase();
            return userRepository.findByEmailAndPassword(emailPreview, password);
        } else {
            throw new JSAuthException("Email is not inserted");
        }
    }

    @Override
    public User registerUser(String email, String username, String password) throws JSAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) {
            String emailPreview = email.toLowerCase(); // Always need to check email in lower case
            if (!pattern.matcher(emailPreview).matches()) {
                throw new JSAuthException("Invalid email format");
            }
            Integer count = userRepository.getCountByEmail(emailPreview);
            if (count > 0) {
                throw new JSAuthException("Email is already used");
            }
            Long userId = userRepository.create(emailPreview, username, password);
            return userRepository.findById(userId);
        } else {
            throw new JSAuthException("Email is not inserted");
        }
    }
}
