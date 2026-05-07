package com.example.scholarship.utils;

import org.springframework.stereotype.Component;

@Component
public class SanitizationUtils {

    public String sanitize(String input) {
        if (input == null) return null;

        return input
                .replaceAll("(?i)<script[^>]*>.*?</script>", "")
                .replaceAll("<[^>]*>", "")
                .replaceAll("(?i)javascript:", "")
                .replaceAll("(?i)on\\w+\\s*=", "")
                .replaceAll("(?i)(DROP|DELETE|INSERT|UPDATE|SELECT|UNION|ALTER)\\s", "")
                .trim();
    }

    public String sanitizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }
}
