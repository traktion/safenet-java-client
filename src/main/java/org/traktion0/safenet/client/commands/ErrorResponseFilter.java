package org.traktion0.safenet.client.commands;

import javax.ws.rs.*;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by paul on 04/09/16.
 */
public class ErrorResponseFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (responseContext.getStatus() < 200 || responseContext.getStatus() >= 300 ) {
            if (responseContext.hasEntity()) {

                String message = responseContext.getStatusInfo().getReasonPhrase();

                Response.Status status = Response.Status.fromStatusCode(responseContext.getStatus());
                WebApplicationException webAppException;
                switch (status) {
                    case BAD_REQUEST:
                        webAppException = new BadRequestException(message);
                        break;
                    case UNAUTHORIZED:
                        webAppException = new NotAuthorizedException(message);
                        break;
                    case FORBIDDEN:
                        webAppException = new ForbiddenException(message);
                        break;
                    case NOT_FOUND:
                        webAppException = new NotFoundException(message);
                        break;
                    case METHOD_NOT_ALLOWED:
                        webAppException = new NotAllowedException(message);
                        break;
                    case NOT_ACCEPTABLE:
                        webAppException = new NotAcceptableException(message);
                        break;
                    case UNSUPPORTED_MEDIA_TYPE:
                        webAppException = new NotSupportedException(message);
                        break;
                    case INTERNAL_SERVER_ERROR:
                        webAppException = new InternalServerErrorException(message);
                        break;
                    case SERVICE_UNAVAILABLE:
                        webAppException = new ServiceUnavailableException(message);
                        break;
                    default:
                        webAppException = new WebApplicationException(message);
                }

                throw webAppException;
            }
        }
    }
}