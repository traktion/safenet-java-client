/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.SafenetFile;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * @author paul
 */
public class GetFile extends SafenetCommand<SafenetFile> {

    private static final String COMMAND_PATH = "/nfs/file/";
    private final long offset;
    private final long length;

    public GetFile(WebTarget webTarget, Auth auth, String queryPath) {
        this(webTarget, auth, queryPath, 0, 0);
    }

    public GetFile(WebTarget webTarget, Auth auth, String queryPath, long offset, long length) {
        super(SafenetFile.class, webTarget, auth, queryPath);

        this.offset = offset;
        this.length = length;
    }

    @Override
    protected SafenetFile getResponse() {
        Response response = doRequest();
        return formatSafenetFile(response);
    }

    private Response doRequest() {
        Builder builder = getWebTarget()
                .path(getPath())
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken());

        if (hasRange()) {
            builder = builder.header("Range", getRangeValue());
        }

        return builder.get();
    }

    private boolean hasRange() {
        return (length > 0);
    }

    private String getRangeValue() {
        return "bytes=" + Long.toString(offset) + "-" + Long.toString(offset+length);
    }

    private SafenetFile formatSafenetFile(Response response) {
        InputStream inputStream = getInputStream(response);
        SafenetFile safenetFile = new SafenetFile();
        safenetFile.setInputStream(inputStream);

        safenetFile.setContentLength(response.getLength());
        safenetFile.setContentRange(response.getHeaderString("content-range"));
        safenetFile.setAcceptRanges(response.getHeaderString("accept-ranges"));
        safenetFile.setContentType(response.getHeaderString("content-type"));
        safenetFile.setCreatedOn(OffsetDateTime.parse(response.getHeaderString("Created-On"))); // 2016-10-05T09:34:44.523Z
        safenetFile.setLastModified(OffsetDateTime.parse(response.getHeaderString("Last-Modified"))); // 2016-10-05T09:34:44.529Z

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
