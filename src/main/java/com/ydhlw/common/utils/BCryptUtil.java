package com.ydhlw.common.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptConfig {
    private BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

    // 加密；返回加密后字符串
    public String encodePassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }

    // 比较；返回true或false
    // rawPassword: 原始密码；encodedPassword: 加密后字符
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
