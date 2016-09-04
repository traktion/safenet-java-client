package org.traktion0.safenet.client.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.traktion0.safenet.client.beans.Auth;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 18/08/16.
 */
public abstract class SafenetCommand<R> extends HystrixCommand<R> {

    private static final int EXEC_TIMEOUT = 30000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private static final String API_VERSION = "0.5";
    private final Class<R> genericClass;
    private final WebTarget webTarget;
    private final Auth auth;
    private final String queryPath;

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

    protected R run() {
        try {
            return getResponse();
        } catch (Exception e) {
            throw new SafenetBadRequestException(e.getCause().getMessage(), e.getCause());
        }
    }

    abstract protected R getResponse();

    protected R getEntity(Response response) {
        return response.readEntity(genericClass);
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
}
