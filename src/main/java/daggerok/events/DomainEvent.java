package daggerok.events;

public interface DomainEvent {
  default String getType() {
    return getClass().getSimpleName();
  }
}
