package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import javax.ws.rs.ClientErrorException;

/**
 * Created by paul on 01/09/16.
 */
public class SafenetBadRequestException extends HystrixBadRequestException {

    private final String reason;
    private final int statusCode;

    public SafenetBadRequestException(String message, Throwable cause) {
        super(message, cause);

        if (cause instanceof ClientErrorException) {
            this.statusCode = ((ClientErrorException) cause).getResponse().getStatus();
            this.reason = ((ClientErrorException) cause).getResponse().getStatusInfo().getReasonPhrase();
        } else {
            this.statusCode = 500;
            this.reason = message;
        }
    }

    public String getReason() {
        return reason;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
