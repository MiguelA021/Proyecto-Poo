package upm.etsisi.poo.es;

public class Command {
  private final static String INCORRECT = "Incorrect Format, please try again.";
  private final static String NOTEXIST = "Product doesn't exist.";
  private final static String WELCOME_MESSAGE = "Welcome to the ticket module App.";
  private final static String HELP_MESSAGE = "Ticket module. Type 'help' to see commands.";
  private final static String FILE_ERROR = "Error while reading the file, please try again.";
  private final static String EMPTY_TICKET = "Empty ticket, try adding some products.";
  private final static String COMMAND_ERROR = "command not found, please try again.";
  public static final String UPM = "tUPM>";
  public static final String ID_REPEAT = "This ID is used, try to use another.";
  private String command;

  public Command(String command) {
    this.command = command;
  }

  public boolean readCommand(Store store, Ticket ticket) {
    boolean end = false;
    String[] commandArray = this.command.split(" ");
    try {
      switch (commandArray[1]) {
        case "add":
          switch (commandArray[0]) {
            case "prod":
              commandProdAdd(commandArray, this.command.split("\""), store);
              break;
            case "ticket":
              commandTicketAdd(commandArray, ticket, store);
              break;
          }
          break;
        case "remove":
          switch (commandArray[0]) {
            case "prod":
              commandProdRemove(commandArray, store);
              break;
            case "ticket":
              commandTicketRemove(commandArray, ticket);
              break;
          }
          break;
        case "list":
          store.prodList();
          break;
        case "update":
          commandProdUpdate(commandArray, store, editSplit(commandArray));
          break;
        case "print":
          commandTicketPrint(ticket);
          break;

        default:
          switch (commandArray[0]) {
            case "help":
              printHelp();
              break;
            case "echo":
              commandEcho(command);
              break;
            case "exit":
              end = true;
              break;
            default:
              System.out.println(INCORRECT);
              break;
          }
      }

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(INCORRECT);
    }
    return end;
  }


  private void commandProdAdd(String[] command, String[] name, Store store) {// TODO
    double price;
    int id;
    Integer maxPers = null;
    boolean maxPersOnProduct=false;
    boolean correct = true;
    boolean add = false;
    String productName;
    String category;
    String[] commandArrayedit = editSplit(command);
    try {
      id = Integer.parseInt(commandArrayedit[2]);
      productName = commandArrayedit[3];
      category = commandArrayedit[4];
      price = Integer.parseInt(commandArrayedit[5]);
      if(commandArrayedit[6]!=null){
        maxPersOnProduct=true;
        maxPers = Integer.valueOf(commandArrayedit[6]);
      }
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
        Product product = null;
        if(maxPersOnProduct){
          product = new Product(id, productName, type.valueOf(category), price, maxPers);
        }else{
          product = new Product(id, productName, type.valueOf(category), price);
        }
        add = store.prodAdd(product);
        if (add) {
          System.out.println(product.toString());
          System.out.println("prod add: ok");
        } else {
          System.out.println(ID_REPEAT);
        }
      } catch (IllegalArgumentException e) {
        System.out.println(INCORRECT);
      }

    }
  }

  private String[] editSplit(String[] commandArray) {
    int length = commandArray.length;
    String[] resul = new String[length];
    int i = 0;
    int pos = 0;
    StringBuilder name = new StringBuilder();
    name.append("\"");
    while (i < length) {
      if (commandArray[i].contains("\"")) {
        boolean fin = false;
        if (commandArray[i].endsWith("\"")) {
          fin = true;
        }
        name.append(commandArray[i].replace("\"", "")).append(" ");
        while (!fin && i < length) {
          i++;
          name.append(commandArray[i].replace("\"", "")).append("\"").append(" ");
          if (commandArray[i].contains("\""))
            fin = true;
        }
        resul[pos] = name.toString();
        pos++;
        i++;
      } else {
        resul[pos] = commandArray[i];
        pos++;
        i++;
      }
    }
    return resul;
  }

  private void commandTicketAdd(String[] commandArray, Ticket ticket, Store store) {
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
      boolean add = ticket.ticketAdd(id, store, amount );
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

  private void commandProdUpdate(String[] commandArray, Store store, String[] name) {
    boolean done = false;
    boolean format;
    Product product;
    switch (commandArray[3]) {
      case "NAME":
        format = true;
        product = store.updateName(Integer.parseInt(commandArray[2]), name[4]);
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;

      case "CATEGORY":
        format = true;
        product = store.updateType(Integer.parseInt(commandArray[2]), type.valueOf(commandArray[4]));
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;
      case "PRICE":
        format = true;
        product = store.updatePrice(Integer.parseInt(commandArray[2]), Double.parseDouble(commandArray[4]));
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;
      default:
        format = false;
        done = false;
        product = null;
        break;
    }
    if (!format) {
      System.out.println(INCORRECT);
    }
    if (format && !done) {
      System.out.println(NOTEXIST);
    }
    if (done && format) {
      System.out.println("prod update: ok");
    }
  }

  private void commandTicketPrint(Ticket ticket) {
    String printed = ticket.ticketPrint();
    if (printed.isEmpty()) {
      System.out.println(EMPTY_TICKET);
    } else {
      System.out.println(printed);
      System.out.println("ticket print: ok");
    }
  }

  private void commandEcho(String command) {
    try {
      String[] parts = command.split("\"");
      System.out.println(parts[0].trim() + " \"" + parts[1].trim() + "\"");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(INCORRECT);
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
    System.out.println("exit\n");
    System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS");
    System.out.println(
        "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.");
  }

}
