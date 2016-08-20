package org.traktion0.safenet.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import javax.ws.rs.core.Response;

/**
 * Created by paul on 18/08/16.
 */
public abstract class SafenetCommand<R> extends HystrixCommand<R> {

    private static final int EXEC_TIMEOUT = 30000;
    private static final String COMMAND_GROUP = "SafeNetCommand";
    private Class<R> genericClass;

    public static final String DRIVE = "drive";
    public static final String APP = "app";

    protected SafenetCommand(Class<R> genericClass) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(EXEC_TIMEOUT)));

        this.genericClass = genericClass;
    }

    protected R getEntity(Response response) {
        if (wasSuccessful(response)) {
            return response.readEntity(genericClass);
        } else {
            Throwable cause = new Throwable(response.getStatusInfo().getReasonPhrase());
            throw new HystrixBadRequestException(Integer.toString(response.getStatus()), cause);
        }
    }

    private boolean wasSuccessful(Response response) {
        return (response.getStatus() >= 200 && response.getStatus() < 300);
    }
}
