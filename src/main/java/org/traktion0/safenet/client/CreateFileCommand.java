package org.traktion0.safenet.client;

import org.traktion0.safenet.client.beans.Token;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * Created by paul on 16/08/16.
 */
public class CreateFileCommand extends SafenetCommand<String> {

    private static final String COMMAND_PATH = "/nfs/file/";

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;
    private final File file;

    public CreateFileCommand(WebTarget webTarget, Token token, String rootPath, String queryPath, File file) {
        super(String.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
        this.file = file;
    }

    @Override
    protected String run() {
        validateSourceFile();

        Response response = webTarget
                .path(COMMAND_PATH + rootPath + "/" +queryPath)
                .request()
                .headers(getHeaders())
                .post(Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM));

        return getEntity(response);
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }

    private void validateSourceFile() {
        if (!file.exists()) {
            Throwable cause = new Throwable("File does not exist");
            throw new HystrixBadRequestException("500", cause);
        }
    }

    private MultivaluedHashMap<String, Object> getHeaders() {
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken());
        headers.add("Content-Length", Long.toString(file.length()));
        headers.add("Content-Type", MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file));
        return headers;
    }
}
