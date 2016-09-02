/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.SafenetDirectory;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author paul
 */
public class GetDirectory extends SafenetCommand<SafenetDirectory> {

    private static final String COMMAND_PATH = "/nfs/directory/";

    public GetDirectory(WebTarget webTarget, Auth auth, String queryPath) {
        super(SafenetDirectory.class, webTarget, auth, queryPath);
    }

    @Override
    protected SafenetDirectory run() {
        Response response = getWebTarget()
                .path(getPath())
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .get();

        return getEntity(response);
    }

    @Override
    protected SafenetDirectory getFallback() {
        return new SafenetDirectory();
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH + getRootPath() + "/";
    }
}
