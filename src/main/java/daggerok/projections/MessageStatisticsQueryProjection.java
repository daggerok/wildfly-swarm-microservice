package daggerok.projections;

import daggerok.events.DomainEvent;
import daggerok.events.EventStore;
import daggerok.events.MessageAdded;
import daggerok.events.MessageCreated;
import daggerok.rest.ZonedDateTimeAdapter;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.*;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.LockType.WRITE;
import static javax.enterprise.event.TransactionPhase.AFTER_COMPLETION;

@Startup
@Singleton
@ApplicationScoped
@ConcurrencyManagement(BEAN)
public class MessageStatisticsQueryProjection {

  @Inject
  EventStore eventStore;

  @Getter long conversations;
  @Getter long totalMessages;

  @Getter
  @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
  //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  //@JsonSerialize(using = ZonedDateTimeSerializer.class)
  //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
  ZonedDateTime lastMessageAt;

  @PostConstruct
  public void rebuildHistory() {
    final Map<UUID, List<DomainEvent>> store = eventStore.findAll();
    final Set<UUID> keys = store.keySet();
    conversations = keys.size();
    totalMessages =
        keys.stream()
            .map(store::get)
            //.flatMap(Collection::stream)
            //.count();
            .mapToLong(Collection::size)
            .sum();
  }

  @Lock(WRITE)
  public void on(@Observes(during = AFTER_COMPLETION) final MessageCreated event) {
    conversations += 1;
    totalMessages += 1;
    lastMessageAt = event.getCreatedAt();
  }

  @Lock(WRITE)
  public void on(@Observes(during = AFTER_COMPLETION) final MessageAdded event) {
    totalMessages += 1;
    lastMessageAt = event.getCreatedAt();
  }
}
