package com.devjefster.autenticacao.util.validator;

import java.util.Set;

public class PasswordValidator {

    private static final Set<String> COMMON_WEAK_PASSWORDS = Set.of(
            "123456", "password", "12345678", "qwerty", "abc123",
            "123456789", "111111", "1234567", "password1", "12345",
            "welcome", "admin", "letmein", "iloveyou", "sunshine",
            "123123", "dragon", "football", "monkey", "princess",
            "solo", "starwars", "passw0rd", "zaq1zaq1", "1qaz2wsx","email"
    );

    public static boolean isWeakPassword(String password) {
        return COMMON_WEAK_PASSWORDS.contains(password);
    }
}
