package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class DeleteAuthToken extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "auth";

    private final WebTarget webTarget;
    private final Token token;

    public DeleteAuthToken(WebTarget webTarget, Token token) {
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
                .delete();

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}
