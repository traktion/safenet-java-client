package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class DeleteDirectory extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;

    public DeleteDirectory(WebTarget webTarget, Token token, String rootPath, String queryPath) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH + rootPath + "/" + queryPath)
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