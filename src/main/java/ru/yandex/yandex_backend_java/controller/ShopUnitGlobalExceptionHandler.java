package ru.yandex.yandex_backend_java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.yandex_backend_java.exception_handling.NoSuchShopUnitException;
import ru.yandex.yandex_backend_java.exception_handling.ShopUnitIncorrectData;

@ControllerAdvice
public class ShopUnitGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ShopUnitIncorrectData> handleException(NoSuchShopUnitException e) {
        ShopUnitIncorrectData data = new ShopUnitIncorrectData();
        data.setMessage(e.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ShopUnitIncorrectData> handleException(NumberFormatException e) {
        ShopUnitIncorrectData data = new ShopUnitIncorrectData();
        data.setCode(HttpStatus.BAD_REQUEST.value());
        data.setMessage(e.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
