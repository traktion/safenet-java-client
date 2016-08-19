/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 *
 * @author paul
 */
public class GetFileCommand extends AbstractCommand<File> {

    private static final String COMMAND_PATH = "/nfs/file/";

    private final String queryPath;
    private final WebTarget webTarget;
    private final Token token;

    public GetFileCommand(WebTarget webTarget, Token token, String queryPath) {
        super(File.class);

        this.webTarget = webTarget;
        this.token = token;
        this.queryPath = queryPath;
    }

    @Override
    protected File run() {
        Response response = webTarget
                .path(COMMAND_PATH+"drive/"+queryPath)
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .get();

        return getEntity(response);
    }

    @Override
    protected File getFallback() {
        return new File("");
    }
}
