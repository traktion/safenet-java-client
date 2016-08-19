package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Directory;
import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class CreateDirectoryCommand extends AbstractCommand<String> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    private final WebTarget webTarget;
    private final Token token;
    private final String queryPath;
    private final Directory directory;

    public CreateDirectoryCommand(WebTarget webTarget, Token token, String queryPath, Directory directory) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.queryPath = queryPath;
        this.directory = directory;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH+"drive/"+queryPath)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .post(Entity.entity(directory, MediaType.APPLICATION_JSON));

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}