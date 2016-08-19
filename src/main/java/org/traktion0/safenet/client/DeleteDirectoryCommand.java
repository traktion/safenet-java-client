package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class DeleteDirectoryCommand extends AbstractCommand<String> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    private final WebTarget webTarget;
    private final Token token;
    private final String queryPath;

    public DeleteDirectoryCommand(WebTarget webTarget, Token token, String queryPath) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.queryPath = queryPath;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH+"drive/"+queryPath)
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