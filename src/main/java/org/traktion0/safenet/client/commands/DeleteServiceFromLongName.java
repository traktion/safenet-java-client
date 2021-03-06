package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.Dns;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class DeleteServiceFromLongName extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/dns/";

    private final Dns dns;

    public DeleteServiceFromLongName(WebTarget webTarget, Auth auth, Dns dns) {
        super(String.class, webTarget, auth);
        this.dns = dns;
    }

    @Override
    protected String getResponse() {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .delete();

        return getEntity(response);
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }

    @Override
    protected String getQueryPath() {
        return dns.getServiceName() + "/" + dns.getLongName();
    }
}

