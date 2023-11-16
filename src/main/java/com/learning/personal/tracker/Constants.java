package com.learning.personal.tracker;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class Constants {
    static SecretKey key = Jwts.SIG.HS256.key().build();
    public static final SecretKey API_SECRET_KEY = key;
    public static final long TOKEN_VALIDITY = 7200000; // 2 Hour
}
