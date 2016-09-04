/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author paul
 */
public class GetAuthToken extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/auth";

    public GetAuthToken(WebTarget webTarget, Auth auth) {
        super(String.class, webTarget, auth);
    }

    @Override
    protected String run() {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .get();

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
