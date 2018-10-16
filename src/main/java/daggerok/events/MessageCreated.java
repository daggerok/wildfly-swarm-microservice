package daggerok.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

import static java.time.ZonedDateTime.now;
import static lombok.AccessLevel.PACKAGE;

@Getter
@AllArgsConstructor(access = PACKAGE)
public class MessageCreated implements DomainEvent {

  final UUID uuid;
  final String message;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonSerialize(using = ZonedDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  ZonedDateTime createdAt;

  public static MessageCreated of(final UUID uuid, final String message) {
    return new MessageCreated(uuid, message, now());
  }
}
