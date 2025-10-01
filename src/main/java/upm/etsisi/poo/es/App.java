package upm.etsisi.poo.es;

import java.lang.reflect.Type;
import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
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
        Ticket ticket = new Ticket();
        Shop shop = new Shop();

        while(!end){
            String command = scan.nextLine();
            String [] commandArray = command.split(" ");
            switch (commandArray[0]){
                case "prod":
                    commandProd(commandArray,shop);
                    break;
                case "ticket":
                    switch (commandArray[1]){
                        case "new":
                            ticket= new Ticket();
                    }
                    break;
                case "help":
                    printHelp();
                    break;
                case "echo":
                    try {
                        String[] commandMarks = command.split("\"");
                        System.out.println(commandMarks[1]);
                    }catch(ArrayIndexOutOfBoundsException){
                        System.out.println("Incorrect format, try again.");
                    }
                    break;
                case "exit":
                    end = true;
                    break;

            }
        }

    }

    private void commandProd(String[] commandArray,Shop shop) {
        switch (commandArray[1]) {
            case "add":
                boolean correct = true;
                try {
                    double price = Double.parseDouble(commandArray[5]);
                    type type = upm.etsisi.poo.es.type.valueOf(commandArray[4]);
                } catch (NumberFormatException e) {
                    System.out.println("Introduce los datos correctos");
                    correct = false;
                }
                if (correct) {
//                            Product product = new Product(commandArray[2],Double.parseDouble(commandArray[5]),commandArray[2],type.valueOf(commandArray[4]),);   falta revisar el constructor.
                    boolean add = shop.addProduct(product);
                    if (add) {
                        System.out.println(product.toString());
                        System.out.println("prod add: ok");
                    }
                }
                break;
            case "list":
                System.out.println(shop.toString());
                break;
            case "update":
                switch (commandArray[3]) {
                    case "NOMBRE":
                        shop.updateName();
                        break;
                    case "CATEGORIA":
                        shop.updateType();
                        break;
                    case "PRECIO":
                        shop.updatePrice();
                        break;
                }
            case "remove":
                break;
        }
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
        System.out.println("exit");
    }

    private void init() {
        System.out.println("Welcome to the ticket module APP.");
        System.out.println("Ticket module. Type 'help' to see commands.");

    }

}
