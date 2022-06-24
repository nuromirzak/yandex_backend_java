package ru.yandex.yandex_backend_java.exception_handling;

public class NoSuchShopUnitException extends RuntimeException {
    public NoSuchShopUnitException(String message) {
        super(message);
    }
}
