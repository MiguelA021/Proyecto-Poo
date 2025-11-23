package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandController {

    private final Store store;
    private final Ticket ticket;
    private final Map<String, Command> commands = new HashMap<>();

    public CommandController(Store store, Ticket ticket) {
        this.store = store;
        this.ticket = ticket;

        // Registrar aquí todos los comandos
        register(new CommandEcho());
        register(new CommandHelp(this));
        register(new ProductCommand());
        register(new TicketCommand());
        register(new ExitCommand());
    }

    private void register(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public boolean handle(String line) {
        if (line == null) return false;
        String trimmed = line.trim();
        if (trimmed.isEmpty()) return false;

        String[] parts = tokenize(trimmed);
        if (parts.length == 0) return false;

        String name = parts[0].toLowerCase();

        Command command = commands.get(name);
        if (command == null) {
            System.out.println(Command.INCORRECT);
            return false;
        }

        return command.execute(trimmed, parts, store, ticket);
    }

    private static String[] tokenize(String line) {

        ArrayList<String> tokens = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);

        while (m.find()) {
            if(m.group(1) == null){
                tokens.add("\"" + m.group(1) + "\"");
            } else {
                tokens.add(m.group(2));
            }
        }

        return tokens.toArray(new String[0]);
    }
}
