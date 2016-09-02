package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.activation.MimetypesFileTypeMap;
import java.io.*;

/**
 * Created by paul on 16/08/16.
 */
public class CreateFile extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/file/";

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;
    private final File file;

    public CreateFile(WebTarget webTarget, Token token, String rootPath, String queryPath, File file) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
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
        Response response = webTarget
                .path(COMMAND_PATH + rootPath + "/" +queryPath)
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
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken());
        headers.add("Content-Length", Long.toString(file.length()));
        headers.add("Content-Type", MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file));
        return headers;
    }
}
