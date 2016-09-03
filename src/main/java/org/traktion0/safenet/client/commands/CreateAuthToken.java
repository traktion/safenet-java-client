/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author paul
  */
public class CreateAuthToken extends SafenetCommand<Auth> {

    private static final String COMMAND_PATH = "/auth";

    private final Auth preAuth;

    public CreateAuthToken(WebTarget webTarget, Auth preAuth) {
        super(Auth.class, webTarget, preAuth);

        this.preAuth = preAuth;
    }
    
    @Override
    protected Auth run() {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .post(Entity.entity(preAuth, MediaType.APPLICATION_JSON));

        Auth auth = getEntity(response);
        auth.setApp(preAuth.getApp());

        return auth;
    }

    @Override
    protected Auth getFallback() {
        return new Auth();
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}
