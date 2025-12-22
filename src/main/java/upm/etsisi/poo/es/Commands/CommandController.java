package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;

import java.util.HashMap;
import java.util.Map;

public class CommandController {

  private final Store store;
  private final Map<String, Command> commands = new HashMap<>();

  public CommandController(Store store) {
    this.store = store;

    // Registrar aquí todos los comandos
    register(new CommandEcho());
    register(new CommandHelp());
    register(new ProductCommand());
    register(new TicketCommand());
    register(new ClientCommand());
    register(new CashierCommand());
    register(new ExitCommand());
  }

  private void register(Command command) {
    commands.put(command.getName(), command);
  }

  public Map<String, Command> getCommands() {
    return commands;
  }

  /** Punto de entrada: recibe lo que escribe el usuario y ejecuta el comando. */
  public boolean handle(String line) {
    if (line == null)
      return false;
    String trimmed = line.trim();
    if (trimmed.isEmpty())
      return false;

    String[] parts = trimmed.split("\\s+");
    String name = parts[0];

    Command command = commands.get(name);
    if (command == null) {
      System.out.println(Command.INCORRECT);
      return false;
    }

    return command.execute(trimmed, parts);
  }
}
