package com.isadora.autenticacao.util.validator;

import java.util.Set;

public class PhoneNumberValidator {

    private static final Set<String> INVALID_PHONE_NUMBERS = Set.of(
            "00000000000", "11111111111", "22222222222", "33333333333",
            "44444444444", "55555555555", "66666666666", "77777777777",
            "88888888888", "99999999999", "12345678900", "98765432100",
            "12312312312", "98798798798"
    );
    private static final String VALID_PHONE_REGEX = "^(\\\\d{10,11})$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the number is in the list of invalid numbers
        if (INVALID_PHONE_NUMBERS.contains(phoneNumber)) {
            return false;
        }

        // Check the number pattern
        return phoneNumber.matches(VALID_PHONE_REGEX);
    }
}

