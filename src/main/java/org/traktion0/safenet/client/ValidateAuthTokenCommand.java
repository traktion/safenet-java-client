/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.traktion0.safenet.client.beans.Token;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author paul
  */
public class ValidateAuthTokenCommand extends HystrixCommand<String> {

    private static final int EXEC_TIMEOUT = 10000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private static final String COMMAND_PATH = "auth";

    private final WebTarget webTarget;
    private final Token token;

    public ValidateAuthTokenCommand(WebTarget webTarget, Token token) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(EXEC_TIMEOUT)));

        this.webTarget = webTarget;
        this.token = token;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .get();

        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            return response.readEntity(String.class);
        } else {
            Throwable cause = new Throwable(response.getStatusInfo().getReasonPhrase());
            throw new HystrixBadRequestException(Integer.toString(response.getStatus()), cause);
        }
    }
}
