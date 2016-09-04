/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.SafenetFile;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * @author paul
 */
public class GetFile extends SafenetCommand<SafenetFile> {

    private static final String COMMAND_PATH = "/nfs/file/";

    public GetFile(WebTarget webTarget, Auth auth, String queryPath) {
        super(SafenetFile.class, webTarget, auth, queryPath);
    }

    @Override
    protected SafenetFile getResponse() {
        Response response = doRequest();
        return formatSafenetFile(response);
    }

    private Response doRequest() {
        return getWebTarget()
                .path(getPath())
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken())
                .get();
    }

    private SafenetFile formatSafenetFile(Response response) {
        InputStream inputStream = getInputStream(response);
        SafenetFile safenetFile = new SafenetFile();
        safenetFile.setInputStream(inputStream);

        safenetFile.setContentLength(response.getLength());
        safenetFile.setContentRange(response.getHeaderString("content-range"));
        safenetFile.setAcceptRanges(response.getHeaderString("accept-ranges"));
        safenetFile.setContentType(response.getHeaderString("content-type"));

        return safenetFile;
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }

    private InputStream getInputStream(Response response) {
        return response.readEntity(InputStream.class);
    }
}
