package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;

public class CommandHelp implements Command {

    private final CommandController controller;

    public CommandHelp(CommandController controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help  - shows available commands";
    }

    @Override
    public boolean execute(String fullLine, String[] args, Store store) {
        System.out.println("Commands:");
        System.out.println("client add \"<nombre>\" <DNI> <email> <cashId>\n" +
                "client remove <DNI>\n"+
                "client list\n"+
                "cash add [<id>] \"<nombre>\"<email>\n"+
                "cash remove <id>\n"+
                "cash list\n"+
                "cash tickets <id>\n"+
                "ticket new [<id>] <cashId> <userId>\n"+
                "ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>]\n"+
                "ticket remove <ticketId><cashId> <prodId>\n"+
                "ticket print <ticketId> <cashId>\n"+
                "ticket list\n"+
                "prod add <id> \"<name>\" <category> <price>\n"+
                "prod update <id> NAME|CATEGORY|PRICE <value>\n"+
                "prod addFood [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n"+
                "prod addMeeting [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n"+
                "prod list\n"+
                "prod remove <id>\n"+
                "help\n"+
                "echo “<text>”\n"+
                "exit\n");
        return false;
    }
}
