package org.traktion0.safenet.client.commands;

import org.apache.commons.lang.StringUtils;
import org.traktion0.safenet.client.beans.Auth;
import org.traktion0.safenet.client.beans.Dns;
import org.traktion0.safenet.client.beans.SafenetDirectory;

import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by paul on 02/09/16.
 */
public class SafenetFactory {
    private final WebTarget webTarget;
    private Auth auth;
    private String rootDirectory;

    private SafenetFactory(WebTarget webTarget, Auth auth, String rootDirectory) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        this.webTarget = webTarget;
        this.auth = auth;
        this.rootDirectory = rootDirectory;

        this.auth = makeCreateAuthTokenCommand().execute();
    }

    public static SafenetFactory getInstance(WebTarget webTarget, Auth auth, String rootDirectory) {
        return new SafenetFactory(webTarget, auth, rootDirectory);
    }

    private String getQueryStringWithRoot(String queryPath) {
        return rootDirectory + "/" + StringUtils.stripStart(queryPath, "/");
    }

    public CreateAuthToken makeCreateAuthTokenCommand() {
        return new CreateAuthToken(webTarget, auth);
    }

    public CreateDirectory makeCreateDirectoryCommand(String queryPath) {
        return makeCreateDirectoryCommand(queryPath, new SafenetDirectory());
    }

    public CreateDirectory makeCreateDirectoryCommand(String queryPath, SafenetDirectory safenetDirectory) {
        return new CreateDirectory(webTarget, auth, getQueryStringWithRoot(queryPath), safenetDirectory);
    }

    public CreateFile makeCreateFileCommand(String queryPath, File file) {
        return new CreateFile(webTarget, auth, getQueryStringWithRoot(queryPath), file);
    }

    public CreateFile makeCreateFileCommand(String queryPath, FileInputStream fileInputStream) {
        return new CreateFile(webTarget, auth, getQueryStringWithRoot(queryPath), fileInputStream);
    }

    public CreateFile makeCreateFileCommand(String queryPath, byte[] bytes) {
        return new CreateFile(webTarget, auth, getQueryStringWithRoot(queryPath), bytes);
    }

    public CreateLongName makeCreateLongNameCommand(String longName) {
        return new CreateLongName(webTarget, auth, longName);
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
        return new DeleteDirectory(webTarget, auth, getQueryStringWithRoot(queryPath));
    }

    public DeleteFile makeDeleteFileCommand(String queryPath) {
        return new DeleteFile(webTarget, auth, getQueryStringWithRoot(queryPath));
    }

    public DeleteLongName makeDeleteLongNameCommand(String longName) {
        return new DeleteLongName(webTarget, auth, longName);
    }

    public DeleteServiceFromLongName makeDeleteServiceFromLongNameCommand(Dns dns) {
        return new DeleteServiceFromLongName(webTarget, auth, dns);
    }

    public GetAuthToken makeGetAuthTokenCommand() {
        return new GetAuthToken(webTarget, auth);
    }

    public GetDirectory makeGetDirectoryCommand(String queryPath) {
        return new GetDirectory(webTarget, auth, getQueryStringWithRoot(queryPath));
    }

    public GetFile makeGetFileCommand(String queryPath) {
        return new GetFile(webTarget, auth, getQueryStringWithRoot(queryPath));
    }

    public GetFile makeGetFileCommand(String queryPath, long offset, long length) {
        return new GetFile(webTarget, auth, getQueryStringWithRoot(queryPath), offset, length);
    }

    public GetFileAttributes makeGetFileAttributesCommand(String queryPath) {
        return new GetFileAttributes(webTarget, auth, getQueryStringWithRoot(queryPath));
    }
}
