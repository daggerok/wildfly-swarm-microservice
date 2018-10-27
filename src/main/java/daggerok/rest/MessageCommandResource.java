package daggerok.rest;

import daggerok.commands.AddMessage;
import daggerok.commands.Command;
import daggerok.commands.CreateMessage;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@Path("/api/v1")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MessageCommandResource {

  @Inject Event<Command> commandGateway;

  @POST
  @Path("/messages")
  public Response post(final Map<String, String> payload) {

    final String message = Optional.ofNullable(payload.get("message"))
                                   .orElseThrow(() -> new IllegalStateException("'message' wasn't found in payload."));
    final CreateMessage command = CreateMessage.of(UUID.randomUUID(), message);

    commandGateway.fire(command);
    return Response.ok(singletonMap("result", command)).build();
  }

  @PUT
  @Path("/messages/{id}")
  public Response put(@PathParam("id") final UUID id, final Map<String, String> payload) {

    final String message = Optional.ofNullable(payload.get("message"))
                                   .orElseThrow(() -> new IllegalStateException("'message' wasn't found in payload."));
    final AddMessage command = AddMessage.of(id, message);

    commandGateway.fire(command);
    return Response.ok(singletonMap("result", command)).build();
  }
}
