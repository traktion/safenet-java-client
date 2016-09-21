package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 21/09/16.
 */
public class DeleteLongName extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/dns/";

    public DeleteLongName(WebTarget webTarget, Auth auth, String longName) {
        super(String.class, webTarget, auth, longName);
    }

    @Override
    protected String getResponse() {
        Response response = getWebTarget()
                .path(getPath())
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .delete();

        return getEntity(response);
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}
