package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.SafenetDirectory;

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

    private final SafenetDirectory safenetDirectory;

    public CreateDirectory(WebTarget webTarget, Auth auth, String queryPath, SafenetDirectory safenetDirectory) {
        super(String.class, webTarget, auth, queryPath);

        this.safenetDirectory = safenetDirectory;
    }

    @Override
    protected String run() {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .post(Entity.entity(safenetDirectory, MediaType.APPLICATION_JSON));

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