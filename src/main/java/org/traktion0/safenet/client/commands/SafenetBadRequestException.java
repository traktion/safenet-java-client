package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import javax.ws.rs.ClientErrorException;

/**
 * Created by paul on 01/09/16.
 */
public class SafenetBadRequestException extends HystrixBadRequestException {

    private final String description;
    private final int statusCode;

    public SafenetBadRequestException(String message, Throwable cause) {
        super(message, cause);

        if (cause instanceof ClientErrorException) {
            this.statusCode = ((ClientErrorException) cause).getResponse().getStatus();
            this.description = ((ClientErrorException) cause).getResponse().getStatusInfo().getReasonPhrase();
        } else {
            this.statusCode = 500;
            this.description = message;
        }
    }

    public String getDescription() {
        return description;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
