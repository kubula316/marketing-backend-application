package com.futurum.marketing.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ErrorResponse(String message, List<String> details) {
    public ErrorResponse(String message) {
        this(message, null);
    }
}
