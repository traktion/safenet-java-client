package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * Created by paul on 01/09/16.
 */
public class SafenetBadRequestException extends HystrixBadRequestException {

    private final String description;
    private final int statusCode;

    public SafenetBadRequestException(String description, int statusCode) {
        super(Integer.toString(statusCode) + ": " + description);

        this.description = description;
        this.statusCode = statusCode;
    }

    public SafenetBadRequestException(String description, int statusCode, Throwable cause) {
        super(Integer.toString(statusCode) + ": " + description, cause);

        this.description = description;
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
