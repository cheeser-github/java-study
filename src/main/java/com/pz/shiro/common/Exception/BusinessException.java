package com.pz.shiro.common.Exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final String message;

    protected final HttpStatus status;

    public BusinessException(String message) {
        this.status = HttpStatus.FORBIDDEN;
        this.message = message;
    }


    public BusinessException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
