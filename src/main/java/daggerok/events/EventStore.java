package daggerok.events;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class EventStore {

  private Map<UUID, List<DomainEvent>> events;

  @PostConstruct
  public void init() {
    events = new ConcurrentHashMap<>();
  }

  public Map<UUID, List<DomainEvent>> findAll() {
    return Collections.unmodifiableMap(events);
  }

  public List<DomainEvent> findByUuid(final UUID uuid) {
    return events.getOrDefault(uuid, new ArrayList<>());
  }

  public void add(final UUID uuid, final DomainEvent event) {
    events.putIfAbsent(uuid, new ArrayList<>());
    events.computeIfPresent(uuid, (key, values) ->
        Stream.concat(values.stream(), Stream.of(event))
              .collect(Collectors.toList()));
  }
}
