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
import java.time.OffsetDateTime;

/**
 * @author paul
 */
public class GetFileAttributes extends SafenetCommand<SafenetFile> {

    private static final String COMMAND_PATH = "/nfs/file/";

    public GetFileAttributes(WebTarget webTarget, Auth auth, String queryPath) {
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
                .head();
    }

    private SafenetFile formatSafenetFile(Response response) {
        SafenetFile safenetFile = new SafenetFile();
        safenetFile.setContentLength(response.getLength());
        safenetFile.setContentRange(response.getHeaderString("Content-Range"));
        safenetFile.setAcceptRanges(response.getHeaderString("Accept-Ranges"));
        safenetFile.setContentType(response.getHeaderString("Content-Type"));
        safenetFile.setCreatedOn(OffsetDateTime.parse(response.getHeaderString("Created-On"))); // 2016-10-05T09:34:44.523Z
        safenetFile.setLastModified(OffsetDateTime.parse(response.getHeaderString("Last-Modified"))); // 2016-10-05T09:34:44.529Z

        return safenetFile;
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}
