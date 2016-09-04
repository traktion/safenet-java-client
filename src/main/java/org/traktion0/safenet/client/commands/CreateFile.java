package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by paul on 16/08/16.
 */
public class CreateFile extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/file/";

    private final File file;

    public CreateFile(WebTarget webTarget, Auth auth, String queryPath, File file) {
        super(String.class, webTarget, auth, queryPath);

        this.file = file;
    }

    @Override
    protected String run() {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return doRequest(inputStream);
        } catch (IOException e) {
            throw new SafenetBadRequestException("Failed to open file input stream", 500, e);
        }
    }

    private String doRequest(InputStream inputStream) {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .headers(getHeaders())
                .post(Entity.entity(inputStream, MediaType.APPLICATION_OCTET_STREAM));

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }

    private MultivaluedHashMap<String, Object> getHeaders() {
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken());
        headers.add("Content-Length", Long.toString(file.length()));
        headers.add("Content-Type", MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file));
        return headers;
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}
