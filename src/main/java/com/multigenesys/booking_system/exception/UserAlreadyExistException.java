package com.multigenesys.booking_system.exception;

import org.springframework.security.core.userdetails.User;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
