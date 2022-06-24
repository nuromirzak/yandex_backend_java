package ru.yandex.yandex_backend_java.helpers;

import ru.yandex.yandex_backend_java.enity.ShopUnit;

import java.util.regex.Pattern;

// https://www.code4copy.com/java/validate-uuid-string-java/
public class Validators {
    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }

    public static boolean validateOffer(ShopUnit shopUnit) {
        if (shopUnit.getPrice() == null || shopUnit.getPrice() < 0) {
            return false;
        }

        return true;
    }

    public static boolean validateCategory(ShopUnit shopUnit) {
        if (shopUnit.getPrice() != null) {
            return false;
        }

        return true;
    }
}
