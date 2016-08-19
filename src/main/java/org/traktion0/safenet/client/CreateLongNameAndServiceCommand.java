package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Dns;
import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class CreateLongNameAndServiceCommand extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/dns";

    private final WebTarget webTarget;
    private final Token token;
    private final Dns dns;

    public CreateLongNameAndServiceCommand(WebTarget webTarget, Token token, Dns dns) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.dns = dns;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .post(Entity.entity(dns, MediaType.APPLICATION_JSON));

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}