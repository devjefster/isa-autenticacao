package com.devjefster.autenticacao.util.validator;

import java.util.Set;

public class EmailValidator {

    private static final Set<String> VALID_EMAIL_DOMAINS = Set.of(
            "gmail.com", "outlook.com", "yahoo.com", "hotmail.com", "icloud.com",
            "protonmail.com", "aol.com", "bol.com", "yourcompany.com"
    );

    public static boolean isValidEmailDomain(String email) {
        String domain = email.substring(email.indexOf('@') + 1);
        return VALID_EMAIL_DOMAINS.contains(domain.toLowerCase());
    }
}
