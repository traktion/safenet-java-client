package org.traktion0.safenet.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.traktion0.safenet.client.beans.Directory;
import org.traktion0.safenet.client.beans.Token;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 06/08/16.
 */
public class CreateLongNameCommand extends HystrixCommand<String> {

    private static final int EXEC_TIMEOUT = 10000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private static final String COMMAND_PATH = "/nfs/directory/";

    private final WebTarget webTarget;
    private final Token token;
    private final String queryPath;

    public CreateLongNameCommand(WebTarget webTarget, Token token, String queryPath) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(EXEC_TIMEOUT)));

        this.webTarget = webTarget;
        this.token = token;
        this.queryPath = queryPath;
    }

    @Override
    protected String run() {
        Response response = webTarget
                .path(COMMAND_PATH+"drive/"+queryPath)
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .post(Entity.entity(directory, MediaType.APPLICATION_JSON));

        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            return response.readEntity(String.class);
        } else {
            Throwable cause = new Throwable(response.getStatusInfo().getReasonPhrase());
            throw new HystrixBadRequestException(Integer.toString(response.getStatus()), cause);
        }
    }

    @Override
    protected String getFallback() {
        return "ERROR";
    }
}