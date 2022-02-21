package com.hawolt.error;

public class AntiCaptchaException extends Exception {

    private final ErrorType type;

    public AntiCaptchaException(ErrorType type) {
        super(type.getDescription());
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
