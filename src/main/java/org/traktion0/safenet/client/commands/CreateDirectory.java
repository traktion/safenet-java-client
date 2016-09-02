package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.SafenetDirectory;
import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class CreateDirectory extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;
    private final SafenetDirectory safenetDirectory;

    public CreateDirectory(WebTarget webTarget, Token token, String rootPath, String queryPath, SafenetDirectory safenetDirectory) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
        this.safenetDirectory = safenetDirectory;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH + rootPath +"/" + queryPath)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .post(Entity.entity(safenetDirectory, MediaType.APPLICATION_JSON));

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}