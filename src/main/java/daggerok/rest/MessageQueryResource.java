package daggerok.rest;

import daggerok.events.EventStore;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Path("")
@Produces(APPLICATION_JSON)
public class MessageQueryResource {

  @Inject
  EventStore eventStore;

  @GET
  @Path("/api/v1/message")
  public Response getAll() {
    return Response.ok(eventStore.findAll()).build();
  }

  @GET
  @Path("/api/v1/message/{uuid}")
  public Response get(@PathParam("uuid") final UUID uuid) {
    return Response.ok(eventStore.findByUuid(uuid)).build();
  }
}
