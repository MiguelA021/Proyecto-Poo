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
        controller.getCommands().values().forEach(cmd ->
                System.out.println(" - " + cmd.getDescription())
        );
        System.out.println(" - exit  - close application");
        System.out.println();
        System.out.println("prod add <id> \"<name>\" <category> <price>");
        System.out.println("prod list");
        System.out.println("prod update <id> NAME|CATEGORY|PRICE <value>");
        System.out.println("prod remove <id>");
        System.out.println("ticket add <prodid> <quantity>");
        System.out.println("ticket remove <prodid>");
        System.out.println("ticket print");
        System.out.println("echo \"<text>\"");
        System.out.println("help");
        System.out.println("exit\n");
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS");
        System.out.println(
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%."
        );
        return false;
    }
}
