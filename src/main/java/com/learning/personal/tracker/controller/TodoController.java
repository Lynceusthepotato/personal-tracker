package com.learning.personal.tracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @GetMapping("")
    public String getAllTodos(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user_id");
        return "Authenticated UserId" + userId;
    }
}
