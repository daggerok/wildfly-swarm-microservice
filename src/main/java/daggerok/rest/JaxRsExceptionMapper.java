package daggerok.rest;

import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Slf4j
@Provider
public class JaxRsExceptionMapper implements ExceptionMapper<Throwable> {

  public Response toResponse(final Throwable exception) {
    log.error("handling fallback: {}", exception.getLocalizedMessage(), exception);
    return Response.status(BAD_REQUEST)
                   .entity(Json.createObjectBuilder()
                               .add("error", exception.getLocalizedMessage())
                               .build())
                   .build();
  }
}
