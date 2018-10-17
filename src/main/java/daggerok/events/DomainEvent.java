package daggerok.events;

import java.util.UUID;

public interface DomainEvent {
  UUID getAggregateId();

  default String getType() {
    return getClass().getSimpleName();
  }
}
