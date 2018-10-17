package daggerok.commands;

import daggerok.events.EventStore;
import daggerok.events.MessageAdded;
import daggerok.events.MessageCreated;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.enterprise.event.TransactionPhase.AFTER_COMPLETION;

@Slf4j
@Startup
@Singleton
@ApplicationScoped
@ConcurrencyManagement(BEAN)
public class CommandGateway {

  @Inject
  EventStore eventStore;

  public void CreateMessageHandler(@Observes(during = AFTER_COMPLETION) final CreateMessage command) {
    final MessageCreated event = MessageCreated.of(command.uuid, command.message);
    eventStore.store(event);
  }

  public void AddMessageHandler(@Observes(during = AFTER_COMPLETION) final AddMessage command) {
    final MessageAdded event = MessageAdded.of(command.uuid, command.message);
    eventStore.store(event);
  }
}
