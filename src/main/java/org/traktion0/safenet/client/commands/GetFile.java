/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.SafenetFile;
import org.traktion0.safenet.client.beans.Token;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 *
 * @author paul
 */
public class GetFile extends SafenetCommand<SafenetFile> {

    private static final String COMMAND_PATH = "/nfs/file/";

    private final WebTarget webTarget;
    private final Token token;
    private final String rootPath;
    private final String queryPath;

    public GetFile(WebTarget webTarget, Token token, String rootPath, String queryPath) {
        super(SafenetFile.class);

        this.webTarget = webTarget;
        this.token = token;
        this.rootPath = rootPath;
        this.queryPath = queryPath;
    }

    @Override
    protected SafenetFile run() {
        Response response = doRequest();
        return formatSafenetFile(response);
    }

    private Response doRequest() {
        return webTarget
                    .path(COMMAND_PATH + rootPath + "/" + queryPath)
                    .request(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
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
    protected SafenetFile getFallback() {
        return new SafenetFile();
    }

    private InputStream getInputStream(Response response) {
        if (wasSuccessful(response)) {
            return response.readEntity(InputStream.class);
        } else {
            throw new SafenetBadRequestException(response.getStatusInfo().getReasonPhrase(), response.getStatus());
        }
    }
}
