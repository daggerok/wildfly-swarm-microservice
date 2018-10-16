package daggerok.rest;

import daggerok.commands.CreateMessage;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MessageCommandResource {

  @Inject
  Event<CreateMessage> commandGateway;

  @POST
  @Path("/api/v1/message")
  public Response post(final Map<String, String> payload) {
    final String message = Optional.ofNullable(payload.get("message"))
                                   .orElseThrow(() -> new IllegalStateException("'message' wasn't found in payload."));
    final CreateMessage command = CreateMessage.of(UUID.randomUUID(), message);
    commandGateway.fire(command);
    return Response.ok(singletonMap("result", command)).build();
  }
}
