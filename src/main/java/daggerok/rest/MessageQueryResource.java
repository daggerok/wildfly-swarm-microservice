package daggerok.rest;

import daggerok.events.EventStore;
import daggerok.projections.MessageStatisticsQueryProjection;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Path("")
@Stateless
@Produces(APPLICATION_JSON)
public class MessageQueryResource {

  @Inject
  EventStore eventStore;

  @Inject
  MessageStatisticsQueryProjection statistics;

  @GET
  @Path("/api/v1/messages")
  public Response getAll() {
    return Response.ok(eventStore.findAll()).build();
  }

  @GET
  @Path("/api/v1/messages/{id}")
  public Response get(@PathParam("id") final UUID id) {
    return Response.ok(eventStore.findByAggregateId(id)).build();
  }

  @GET
  @Path("/api/v1/statistics")
  public Response getStatistics() {
    final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    jsonObjectBuilder.add("total conversations", statistics.getConversations());
    jsonObjectBuilder.add("total messages", statistics.getTotalMessages());

    Optional.ofNullable(statistics.getLastMessageAt())
            .ifPresent(zonedDateTime -> jsonObjectBuilder
                .add("last message at", zonedDateTime.toString()));

    return Response.ok(jsonObjectBuilder.build())
                   .build();
  }
}
