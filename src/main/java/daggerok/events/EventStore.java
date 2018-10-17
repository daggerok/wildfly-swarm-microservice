package daggerok.events;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Startup
@Singleton
@ApplicationScoped
@ConcurrencyManagement(BEAN)
public class EventStore {

  @Inject
  Event<DomainEvent> domainEvents;

  Map<UUID, List<DomainEvent>> store;

  @PostConstruct
  public void init() {
    store = new ConcurrentHashMap<>();
  }

  public Map<UUID, List<DomainEvent>> findAll() {
    return Collections.unmodifiableMap(store);
  }

  public List<DomainEvent> findByAggregateId(final UUID aggregateId) {
    return store.getOrDefault(aggregateId, new ArrayList<>());
  }

  @Asynchronous
  @Transactional
  public void store(final DomainEvent event) {
    final UUID aggregateId = event.getAggregateId();
    store.putIfAbsent(aggregateId, new ArrayList<>());
    store.computeIfPresent(aggregateId, (id, events) ->
        Stream.concat(events.stream(), Stream.of(event))
              .collect(Collectors.toList()));
    domainEvents.fire(event);
  }
}
