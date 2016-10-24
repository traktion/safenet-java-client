package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;

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
        } else if (cause instanceof ServerErrorException) {
            this.statusCode = ((ServerErrorException) cause).getResponse().getStatus();
            this.reason = ((ServerErrorException) cause).getResponse().getStatusInfo().getReasonPhrase();
        } else if (cause instanceof WebApplicationException) {
            this.statusCode = ((WebApplicationException) cause).getResponse().getStatus();
            this.reason = ((WebApplicationException) cause).getResponse().getStatusInfo().getReasonPhrase();
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
