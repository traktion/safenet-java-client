package org.traktion0.safenet.client.commands;

import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.Dns;
import org.traktion0.safenet.client.beans.SafenetDirectory;

import javax.ws.rs.client.WebTarget;
import java.io.File;

/**
 * Created by paul on 02/09/16.
 */
public class SafenetFactory {
    private final WebTarget webTarget;
    private Auth auth;

    private SafenetFactory(WebTarget webTarget, Auth auth) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        this.webTarget = webTarget;
        this.auth = auth;

        this.auth = makeCreateAuthTokenCommand().execute();
    }

    public static SafenetFactory getInstance(WebTarget webTarget, Auth auth) {
        return new SafenetFactory(webTarget, auth);
    }

    public CreateAuthToken makeCreateAuthTokenCommand() {
        return new CreateAuthToken(webTarget, auth);
    }

    public CreateDirectory makeCreateDirectoryCommand(String queryPath) {
        return makeCreateDirectoryCommand(queryPath, new SafenetDirectory());
    }

    public CreateDirectory makeCreateDirectoryCommand(String queryPath, SafenetDirectory safenetDirectory) {
        return new CreateDirectory(webTarget, auth, queryPath, safenetDirectory);
    }

    public CreateFile makeCreateFileCommand(String queryPath, File file) {
        return new CreateFile(webTarget, auth, queryPath, file);
    }

    public CreateLongNameAndService makeCreateLongNameAndServiceCommand(Dns dns) {
        return new CreateLongNameAndService(webTarget, auth, dns);
    }

    public CreateService makeCreateServiceCommand(Dns dns) {
        return new CreateService(webTarget, auth, dns);
    }

    public DeleteAuthToken makeDeleteAuthTokenCommand() {
        return new DeleteAuthToken(webTarget, auth);
    }

    public DeleteDirectory makeDeleteDirectoryCommand(String queryPath) {
        return new DeleteDirectory(webTarget, auth, queryPath);
    }

    public DeleteFile makeDeleteFileCommand(String queryPath) {
        return new DeleteFile(webTarget, auth, queryPath);
    }

    public DeleteServiceFromLongName makeDeleteServiceFromLongNameCommand(Dns dns) {
        return new DeleteServiceFromLongName(webTarget, auth, dns);
    }

    public GetAuthToken makeGetAuthTokenCommand() {
        return new GetAuthToken(webTarget, auth);
    }

    public GetDirectory makeGetDirectoryCommand(String queryPath) {
        return new GetDirectory(webTarget, auth, queryPath);
    }

    public GetFile makeGetFileCommand(String queryPath) {
        return new GetFile(webTarget, auth, queryPath);
    }
}
