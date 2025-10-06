package upm.etsisi.poo.es;
//BORAR ESTE COMENTARIO

import java.lang.reflect.Type;
import java.util.Scanner;

public class App {
    private final static String INCORRECT = "Incorrect Format, please try again.";
    private final static String NOTEXIST = "Product doesn't exist.";
    private final static String WELCOME_MESSAGE="Welcome to the ticket module APP.";
    private final static String HELP_MESSAGE= "Ticket module. Type 'help' to see commands.";

    public static void main(String[] args) {
        App app = new App();
        app.init();
        app.start();
        app.end();

    }

    private void end() {
    }

    private void start() {
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        Store store = new Store();
        Ticket ticket = new Ticket(store);
        while (!end) {
            String command = scan.nextLine();
            String[] commandArray = command.split(" ");
            switch (commandArray[0]) {
                case "prod":
                    commandProd(commandArray, store, ticket);
                    break;
                case "ticket":
                    commandTicket(commandArray, ticket, store);
                    break;
                case "help":
                    printHelp();
                    break;
                case "echo":
                    commandEcho(command);
                    break;
                case "exit":
                    end = true;
                    break;

            }
        }

    }

    private void commandEcho(String command) {
        try {
            String[] commandMarks = command.split("\"");
            System.out.println(commandMarks[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(INCORRECT);
        }
    }

    private void commandTicket(String[] commandArray, Ticket ticket, Store store) {
        switch (commandArray[1]) {
            case "new":
                commandTicketNew(ticket);
                break;
            case "add":
                commandTicketAdd(commandArray, ticket);
                break;
            case "remove":
                commandTicketRemove(commandArray, ticket);
                break;
            case "print":
                commandTicketPrint(ticket);
                break;

        }
    }

    private void commandTicketNew(Ticket ticket) {
        ticket.ticketNew();
        System.out.println("ticket new: ok");
    }

    private void commandTicketPrint(Ticket ticket) {
        System.out.println(ticket.ticketPrint());
        System.out.println("ticket print: ok");
    }

    private void commandTicketAdd(String[] commandArray, Ticket ticket) {
    }

    private void commandTicketRemove(String[] commandArray, Ticket ticket) {
        int id;
        boolean correct = true;
        try {
            id = Integer.parseInt(commandArray[2]);
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            correct = false;
            id = -1;
        }
        if (correct) {
            Product product = ticket.ticketRemove(id);
            if (product == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(product);
                System.out.println("ticket remove: ok");
            }
        }
    }

    private void commandProd(String[] commandArray, Store store, Ticket ticket) {

        switch (commandArray[1]) {
            case "add":
                commandProdAdd(commandArray, store);
                break;
            case "list":
                commandProdList(store);
                break;
            case "update":
                commandProUpdate(commandArray, store);

            case "remove":
                commandProdRemove(commandArray, store);
                break;
        }
    }

    private void commandProdList(Store store) {
        Product[] productList = store.getProducts();
        int length = productList.length;
        for (int i = 0; i < productList.length; i++) {
            System.out.println(productList[i].toString());
        }
        System.out.println("prod list: ok");
    }

    private void commandProdRemove(String[] commandArray, Store store) {
        boolean correct = true;
        int id;
        boolean remove;
        try {
            id = Integer.parseInt(commandArray[2]);
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            correct = false;
            id = -1;
        }
        if (correct) {
            Product product = null;                 // revisar
            remove = store.prodRemove(id);
            if (remove) {
                product.toString();

            }
        }
    }

    private void commandProUpdate(String[] commandArray, Store store) {
        boolean done;
        boolean format;
        switch (commandArray[3]) {
            case "NOMBRE":
                format = true;
                done = store.updateName(Integer.getInteger(commandArray[4]), commandArray[5]);
                break;
            case "CATEGORIA":
                format = true;
                done = store.updateType(Integer.getInteger(commandArray[4]), type.valueOf(commandArray[5]));
                break;
            case "PRECIO":
                format = true;
                done = store.updatePrice(Integer.getInteger(commandArray[4]), Double.parseDouble(commandArray[5]));
                break;
            default:
                format = false;
                done = false;
                break;
        }
        if (format) {
            System.out.println(INCORRECT);
        }
        if (format && !done) {
            System.out.println(NOTEXIST);
        }
        if (done && format) {
            System.out.println("prod update: ok");
        }
    }

    private void commandProdAdd(String[] command, Store store) {
        double price;
        int id;
        boolean correct = true;
        boolean add = false;
        try {
            id = Integer.parseInt(command[2]);
            price = Double.parseDouble(command[5]);
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            correct = false;
            id = -1;
            price = -1;
        }
        if (correct) {
            String category = command[4];
            String name = command[3].replace("\"", "");
            try {
                Product product = new Product(id, name, type.valueOf(category), price);
                add = store.prodAdd(product);
                if (add) {
                    System.out.println(product.toString());
                    System.out.println("pro add: ok");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(INCORRECT);
            }

        }
    }

    /**
     * The method prints all the commands allowed and their format
     */
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
        System.out.println("exit");
    }

    /**
     * It initializes the App
     */
    private void init() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(HELP_MESSAGE);

    }

}
