/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.SafenetDirectory;
import org.traktion0.safenet.client.beans.Token;
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

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;

    public GetDirectory(WebTarget webTarget, Token token, String rootPath, String queryPath) {
        super(SafenetDirectory.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
    }

    @Override
    protected SafenetDirectory run() {
        Response response = webTarget
                .path(COMMAND_PATH + rootPath + "/" + queryPath)
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .get();

        return getEntity(response);
    }

    @Override
    protected SafenetDirectory getFallback() {
        return new SafenetDirectory();
    }
}
