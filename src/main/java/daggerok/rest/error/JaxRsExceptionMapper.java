package daggerok.rest.error;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URL;
import java.util.function.BiFunction;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Slf4j
@Provider
public class JaxRsExceptionMapper implements ExceptionMapper<Throwable> {

  @Context UriInfo uriInfo;

  final BiFunction<String, String, String> url = (method, path) -> {
    final Try<URL> tryUrl = Try.of(() -> uriInfo.getBaseUriBuilder().build().toURL());
    if (tryUrl.isFailure()) return format("%s %s", method, path);
    final URL url = tryUrl.get();
    final String protocol = url.getProtocol();
    final String authority = url.getAuthority();
    return format("%s %s://%s%s", method, protocol, authority, path);
  };

  final JsonArray commands = Json.createArrayBuilder()
                                 .add(url.apply("POST", "/api/v1/messages"))
                                 .add(url.apply("PUT", "/api/v1/messages/{id}"))
                                 .build();

  final JsonArray queries = Json.createArrayBuilder()
                                .add(url.apply("GET", "/api/v1/messages"))
                                .add(url.apply("GET", "/api/v1/messages/{id}"))
                                .add(url.apply("GET", "/api/v1/statistics"))
                                .build();

  public Response toResponse(final Throwable e) {

    final String error = format("%s - %s", e.getClass().getSimpleName(), e.getLocalizedMessage());
    log.error("handling fallback: {}", e.getLocalizedMessage(), e);

    return Response.status(BAD_REQUEST)
                   .entity(Json.createObjectBuilder()
                               .add("error", error)
                               .add("available commands", commands)
                               .add("available queries", queries)
                               .build())
                   .build();
  }
}
