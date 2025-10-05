package upm.etsisi.poo.es;
//BORAR ESTE COMENTARIO

import java.lang.reflect.Type;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app =new App();
        app.init();
        app.start();
        app.end();

    }
    /**
     * It initializes the App
     */
    private void init() {
        System.out.println("Welcome to the ticket module APP.");
        System.out.println("Ticket module. Type 'help' to see commands.");

    }


    private void end() {
    }

    private void start() {
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        Ticket ticket = new Ticket();
        Store store = new Store();


        while (!end) {
            String command = scan.nextLine().replace("\\s+", " ");
            String[] commandArray = command.split(" ");
            switch (commandArray[0]) {
                case "prod":
                    commandProd(commandArray, store, ticket);
                    break;
                case "ticket":
                    switch (commandArray[1]) {
                        case "new":
                            ticket = new Ticket();
                    }
                    break;
                case "help":
                    printHelp();
                    break;
                case "echo":
                    try {
                        String[] commandMarks = command.split("\"");
                        System.out.println(commandMarks[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Incorrect format, try again.");
                    }
                    break;
                case "exit":
                    end = true;
                    break;

            }
        }

    }

    private void commandProd(String[] commandArray, Store store, Ticket ticket) {
        switch (commandArray[1]) {
            case "add":
                commandProdAdd(commandArray,store);
                break;
            case "list":
                System.out.println(store.prodList());
                break;
            case "update":
                commandProUpdate(commandArray,store);

            case "remove":
                break;
        }
    }

    private void commandProUpdate(String[] commandArray, Store store) {
        switch (commandArray[3]) {
            case "NOMBRE":
                store.updateName(Integer.getInteger(commandArray[4]), commandArray[5]);
                break;
            case "CATEGORIA":
                store.updateType(Integer.getInteger(commandArray[4]), type.valueOf(commandArray[5]));
                break;
            case "PRECIO":
                store.updatePrice(Integer.getInteger(commandArray[4]), Double.parseDouble(commandArray[5]));
                break;
        }
    }
    }

    private void commandProdAdd(String[] command, Store store) {
        double price = -1;
        int id = -1;
        boolean correct = true;
        boolean add = false;
        try {
            id = Integer.parseInt(command[2]);
            price = Double.parseDouble(command[5]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format, please try again");
            correct = false;
        }
        if (correct) {
            String category = command[4];
            String name = command[3].replace("\"", "");
            Product product = new Product(id, name, type.valueOf(category), price);
            add = store.prodAdd(product);
            if (add) {
                System.out.println(product.toString());
                System.out.println("pro add: ok");
            }

        }

        /**
         * The method prints all the commands allowed and their format
         */
        private void printHelp () {
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

    }
