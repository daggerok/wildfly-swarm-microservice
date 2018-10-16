package daggerok.commands;

import daggerok.events.EventStore;
import daggerok.events.MessageCreated;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.UUID;

@Slf4j
public class CreateMessageCommandGateway {

  @Inject
  EventStore eventStore;

  @Inject
  Event<MessageCreated> events;

  void CreateMessageHandler(@Observes final CreateMessage command) {
    final UUID uuid = command.uuid;
    final MessageCreated event = MessageCreated.of(uuid, command.message);
    eventStore.add(uuid, event);
    events.fire(event);
  }
}
