package daggerok.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CreateMessage implements Command {

  final UUID uuid;
  final String message;
}
