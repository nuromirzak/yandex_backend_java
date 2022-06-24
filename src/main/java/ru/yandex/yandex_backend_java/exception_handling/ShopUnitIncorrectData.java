package ru.yandex.yandex_backend_java.exception_handling;

public class ShopUnitIncorrectData {
    private Integer code;
    private String message;

    public ShopUnitIncorrectData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
