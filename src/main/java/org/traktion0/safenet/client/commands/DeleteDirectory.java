package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class DeleteDirectory extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    public DeleteDirectory(WebTarget webTarget, Auth auth, String queryPath) {
        super(String.class, webTarget, auth, queryPath);
    }

    @Override
    protected String run() {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .delete();

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}