package com.massbranches.pgr_api.exception;

public record DefaultErrorMessage(
        int status,
        String message
) {
}
