package upm.etsisi.poo.es.Commands;

public class CommandHelp extends Command {
    public CommandHelp(String command) {
        super(command);
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("pro add <id> \"<name>\"<category><price>");
        System.out.println("prod list");
        System.out.println("prod update <id>NAME|CATEGORY|PRICE<value>");
        System.out.println("prod remove<id>");
        System.out.println("ticket new");
        System.out.println("ticket add<prodid>>quantity>");
        System.out.println("ticket remove<prodid>");
        System.out.println("ticket print");
        System.out.println("echo\"<texto>\"");
        System.out.println("help");
        System.out.println("exit\n");
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS");
        System.out.println(
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.");
    }
}
