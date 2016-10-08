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
    private final FileInputStream fileInputStream;
    private final byte[] bytes;

    public CreateFile(WebTarget webTarget, Auth auth, String queryPath, File file) {
        super(String.class, webTarget, auth, queryPath);

        this.file = file;
        this.fileInputStream = null;
        this.bytes = null;
    }

    public CreateFile(WebTarget webTarget, Auth auth, String queryPath, FileInputStream fileInputStream) {
        super(String.class, webTarget, auth, queryPath);

        this.file = null;
        this.fileInputStream = fileInputStream;
        this.bytes = null;
    }

    public CreateFile(WebTarget webTarget, Auth auth, String queryPath, byte[] bytes) {
        super(String.class, webTarget, auth, queryPath);

        this.file = null;
        this.fileInputStream = null;
        this.bytes = bytes;
    }

    @Override
    protected String getResponse() {
        if (file != null) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                return doRequest(inputStream);
            } catch (IOException e) {
                throw new SafenetBadRequestException("Failed to open file input stream", e);
            }
        } else if (fileInputStream != null) {
            try {
                return doRequest(fileInputStream);
            } catch (IOException e) {
                throw new SafenetBadRequestException("Failed to fetch the size of the file input stream to be saved", e);
            }
        } else {
            try {
                return doRequest(bytes);
            } catch (IOException e) {
                throw new SafenetBadRequestException("Failed to fetch the size of the byte array to be saved", e);
            }
        }
    }

    private String doRequest(InputStream inputStream) throws IOException {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .headers(getHeaders())
                .post(Entity.entity(inputStream, MediaType.APPLICATION_OCTET_STREAM));

        return getEntity(response);
    }

    private String doRequest(byte[] bytes) throws IOException {
        Response response = getWebTarget()
                .path(getPath())
                .request()
                .headers(getHeaders())
                .post(Entity.entity(bytes, MediaType.APPLICATION_OCTET_STREAM));

        return getEntity(response);
    }

    private MultivaluedHashMap<String, Object> getHeaders() throws IOException {
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAuth().getToken());
        headers.add("Content-Length", Long.toString(getContentLength()));
        headers.add("Content-Type", getContentType());
        return headers;
    }

    private long getContentLength() throws IOException {
        if (file != null) {
            return file.length();
        } else if (fileInputStream != null) {
            return fileInputStream.getChannel().size();
        } else {
            return bytes.length;
        }
    }

    private String getContentType() throws IOException {
        if (file != null) {
            return MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file);
        } else {
            return MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(getQueryPath());
        }
    }

    @Override
    protected String getCommandPath() {
        return COMMAND_PATH;
    }
}
