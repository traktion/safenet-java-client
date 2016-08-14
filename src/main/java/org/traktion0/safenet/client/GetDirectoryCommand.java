/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.traktion0.safenet.client;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.traktion0.safenet.client.beans.Directory;
import org.traktion0.safenet.client.beans.Token;

/**
 *
 * @author paul
 */
public class GetDirectoryCommand extends HystrixCommand<Directory> {

    private static final int EXEC_TIMEOUT = 10000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private static final String COMMAND_PATH = "/nfs/directory/";

    private final String queryPath;
    private final WebTarget webTarget;
    private final Token token;

    public GetDirectoryCommand(WebTarget webTarget, Token token, String queryPath) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(EXEC_TIMEOUT)));

        this.webTarget = webTarget;
        this.token = token;
        this.queryPath = queryPath;
    }

    @Override
    protected Directory run() {
        Response response = webTarget
                .path(COMMAND_PATH+"drive/"+queryPath)
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken())
                .get();

        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            return response.readEntity(Directory.class);
        } else {
            Throwable cause = new Throwable(response.getStatusInfo().getReasonPhrase());
            throw new HystrixBadRequestException(Integer.toString(response.getStatus()), cause);
        }
    }
}
