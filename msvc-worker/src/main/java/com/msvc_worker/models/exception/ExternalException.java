package com.msvc_worker.models.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExternalException extends RuntimeException {

    private final HttpStatus status;
    private final String responseBody;

    public ExternalException(HttpStatus status,String responseBody) {
        super("Error al consumir api Externa - Status: " + status + " , Body: " + responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }
}
