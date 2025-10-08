package upm.etsisi.poo.es;
//BORAR ESTE COMENTARIO

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
  private final static String INCORRECT = "Incorrect Format, please try again.";
  private final static String NOTEXIST = "Product doesn't exist.";
  private final static String WELCOME_MESSAGE = "Welcome to the ticket module APP.";
  private final static String HELP_MESSAGE = "Ticket module. Type 'help' to see commands.";
  private final static String FILE_ERROR = "Error while reading the file, please try again";

  public static void main(String[] args) {
    App app = new App();
    app.init();
    app.start(args);
    app.end();

  }

  private void end() {
  }

  public void start(String[] args) {
    if (args.length == 0) {
      userCommand();
    } else {
      readFile(args);
    }

  }

  public void userCommand() {
    boolean end = false;
    Scanner scan = new Scanner(System.in);
    Store store = new Store();
    Ticket ticket = new Ticket(store);
    while (!end) {
      String command = scan.nextLine();
      end = readCommand(command, store, ticket);
    }
    scan.close();
  }

  private void readFile(String[] args) {
    String command;
    Store store = new Store();
    Ticket ticket = new Ticket(store);
    try {
      BufferedReader reader = new BufferedReader(new FileReader(args[0]));
      boolean end = false;
      while (!end) {
        command = reader.readLine();
        if (command != null) {
          end = readCommand(command, store, ticket);
        }
        reader.close();
      }
    } catch (IOException e) {
      System.out.println(FILE_ERROR);
    }
  }

  private boolean readCommand(String command, Store store, Ticket ticket) {
    boolean end = false;
    while (!end) {
      String[] commandArray = command.split(" ");
      switch (commandArray[0]) {
        case "prod":
          commandProd(commandArray, store, ticket, command);
          break;
        case "ticket":
          commandTicket(commandArray, ticket);
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
    return end;
  }

  private void commandEcho(String command) {
    try {
      String[] commandMarks = command.split("\"");
      System.out.println(commandMarks[1]);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(INCORRECT);
    }
  }

  private void commandTicket(String[] commandArray, Ticket ticket) {
    switch (commandArray[1]) {
      case "new":
        ticket.ticketNew();
        System.out.println("ticket new: ok");
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

  private void commandTicketAdd(String[] commandArray, Ticket ticket) {
    int id;
    int amount;
    boolean correct = true;
    try {
      id = Integer.parseInt(commandArray[2]);
      amount = Integer.parseInt(commandArray[3]);
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      id = -1;
      amount = -1;
      correct = false;
    }
    if (correct) {
      boolean add = ticket.ticketAdd(id, amount);
    }
  }

  private void commandTicketPrint(Ticket ticket) {
    System.out.println(ticket.ticketPrint());
    System.out.println("ticket print: ok");
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
        System.out.println(product.toString());
        System.out.println("ticket remove: ok");
      }
    }
  }

  private void commandProd(String[] commandArray, Store store, Ticket ticket, String command) {

    switch (commandArray[1]) {
      case "add":
        String[] name = command.split("\"");
        commandProdAdd(commandArray, name, store);
        break;
      case "list":
        store.prodList();
        break;
      case "update":
        commandProUpdate(commandArray, store);

      case "remove":
        commandProdRemove(commandArray, store);
        break;
    }
  }

  private void commandProdRemove(String[] commandArray, Store store) {
    boolean correct = true;
    int id;
    try {
      id = Integer.parseInt(commandArray[2]);
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      correct = false;
      id = -1;
    }
    if (correct) {
      store.prodRemove(id);
    }
  }

  private void commandProUpdate(String[] commandArray, Store store) {
    boolean done;
    boolean format;
    switch (commandArray[3]) {
      case "NAME":
        format = true;
        done = store.updateName(Integer.getInteger(commandArray[4]), commandArray[5]);
        break;
      case "CATEGORY":
        format = true;
        done = store.updateType(Integer.getInteger(commandArray[4]), type.valueOf(commandArray[5]));
        break;
      case "PRICE":
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

  private void commandProdAdd(String[] command, String[] name, Store store) {// TODO
    double price;
    int id;
    boolean correct = true;
    boolean add = false;
    String productName;
    String category;
    try {
      String[] cat_Price = name[2].split(" ");
      id = Integer.parseInt(command[2]);
      price = Double.parseDouble(cat_Price[1]);
      productName = name[1];
      category = cat_Price[0];
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      correct = false;
      productName = "ERROR";
      category = "ERROR";
      id = -1;
      price = -1;
    }
    if (correct) {
      try {
        Product product = new Product(id, productName, type.valueOf(category), price);
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
