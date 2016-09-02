package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.traktion0.safenet.client.beans.Auth;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * Created by paul on 18/08/16.
 */
public abstract class SafenetCommand<R> extends HystrixCommand<R> {

    private static final int EXEC_TIMEOUT = 30000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private static final String API_VERSION = "0.5";
    private Class<R> genericClass;

    public static final String DRIVE = "drive";
    public static final String APP = "app";

    private final WebTarget webTarget;
    private final Auth auth;
    private String queryPath;

    protected SafenetCommand(Class<R> genericClass, WebTarget webTarget, Auth auth) {
        this(genericClass, webTarget, auth, "");
    }

    protected SafenetCommand(Class<R> genericClass, WebTarget webTarget, Auth auth, String queryPath) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(EXEC_TIMEOUT)));

        this.genericClass = genericClass;
        this.webTarget = webTarget;
        this.auth = auth;
        this.queryPath = queryPath;
    }

    protected R getEntity(Response response) {
        if (wasSuccessful(response)) {
            return response.readEntity(genericClass);
        } else {
            throw new SafenetBadRequestException(response.getStatusInfo().getReasonPhrase(), response.getStatus());
        }
    }

    protected boolean wasSuccessful(Response response) {
        return (response.getStatus() >= 200 && response.getStatus() < 300);
    }

    protected String getPath() {
        return API_VERSION + getCommandPath() + getQueryPath();
    }

    abstract protected String getCommandPath();

    protected String getQueryPath() {
        return queryPath;
    }

    protected WebTarget getWebTarget() {
        return webTarget;
    }

    protected Auth getAuth() {
        return auth;
    }

    protected String getRootPath() {
        if (Arrays.asList(getAuth().getPermissions()).contains("SAFE_DRIVE_ACCESS")) {
            return DRIVE;
        }
        return APP;
    }
}
