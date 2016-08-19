/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author paul
  */
public class CreateAuthTokenCommand extends AbstractCommand<Token> {

    private static final String COMMAND_PATH = "auth";

    private final WebTarget webTarget;
    private final Auth auth;

    public CreateAuthTokenCommand(WebTarget webTarget, Auth auth) {
        super(Token.class);

        this.webTarget = webTarget;
        this.auth = auth;
    }
    
    @Override
    protected Token run() {
        Response response = webTarget
                .path(COMMAND_PATH)
                .request()
                .post(Entity.entity(auth, MediaType.APPLICATION_JSON));

        return getEntity(response);
    }

    @Override
    protected Token getFallback() {
        return new Token();
    }
}
