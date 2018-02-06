package com.srct.ril.poas.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceException extends Exception {
	public ServiceException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
