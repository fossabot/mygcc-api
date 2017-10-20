package com.mygcc.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler class for exceptions.
 */
@Provider
public class InternalServerErrorExceptionHandler implements
        ExceptionMapper<Throwable> {

    /**
     * Generate and return a response to exceptions.
     * @param ex Throwable object
     * @return Response to be delivered to client
     */
    public final Response toResponse(final Throwable ex) {
        // Log error
        // Saves to Heroku logs
        ex.printStackTrace();

        // Generate default error message
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Internal server error");
        response.put("date", Instant.now().getEpochSecond());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .type("application/json")
                .build();
    }
}
