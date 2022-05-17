package com.bitmoi.user.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginException extends ResponseStatusException {

    public LoginException(HttpStatus status) {
        super(status);
        // TODO Auto-generated constructor stub
    }

    public LoginException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public LoginException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public LoginException(String reason, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, reason, cause);
    }
}
