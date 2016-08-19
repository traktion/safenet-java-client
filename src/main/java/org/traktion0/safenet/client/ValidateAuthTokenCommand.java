/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author paul
  */
public class ValidateAuthTokenCommand extends AbstractCommand<String> {

    private static final String COMMAND_PATH = "auth";

    private final WebTarget webTarget;
    private final Token token;

    public ValidateAuthTokenCommand(WebTarget webTarget, Token token) {
        super(String.class);

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

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}
