package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.type;

public abstract class Command {
  public final static String INCORRECT = "Incorrect Format, please try again.";
  public final static String NOTEXIST = "Product doesn't exist.";
  public final static String EMPTY_TICKET = "Empty ticket, try adding some products.";
  public static final String ID_REPEAT = "This ID is used, try to use another.";
  private String command;

  public Command(String command) {
    this.command = command;
  }
  // public boolean readCommand(Store store, Ticket ticket) {
  // boolean end = false;
  // String[] commandArray = this.command.split(" ");
  // try {
  // switch (commandArray[2]) {
  // case "add":
  // switch (commandArray[0]) {
  // case "prod":
  // commandProdAdd(commandArray, this.command.split("\""), store);
  // break;
  // case "ticket":
  // commandTicketAdd(commandArray, ticket, store);
  // break;
  // }
  // break;
  // case "remove":
  // switch (commandArray[0]) {
  // case "prod":
  // commandProdRemove(commandArray, store);
  // break;
  // case "ticket":
  // commandTicketRemove(commandArray, ticket);
  // break;
  // }
  // break;
  // case "list":
  // store.prodList();
  // break;
  // case "update":
  // commandProdUpdate(commandArray, store, editSplit(commandArray));
  // break;
  // case "print":
  // commandTicketPrint(ticket);
  // break;
  //
  // default:
  // switch (commandArray[0]) {
  // case "help":
  // printHelp();
  // break;
  // case "echo":
  // commandEcho(command);
  // break;
  // case "exit":
  // end = true;
  // break;
  // default:
  // System.out.println(INCORRECT);
  // break;
  // }
  // }
  //
  // } catch (ArrayIndexOutOfBoundsException e) {
  // System.out.println(INCORRECT);
  // }
  // return end;
  // }

  public String[] editSplit(String[] commandArray) {
    int length = commandArray.length;
    String[] resul = new String[length];
    int i = 0; // contador de commandArray
    int pos = 0; // contador de resul
    StringBuilder name = new StringBuilder();
    while (i < length) {
      if (commandArray[i].contains("\"")) {
        boolean fin = false;
        if (commandArray[i].endsWith("\"")) {
          fin = true;
        }
        name.append(commandArray[i].replace("\"", "")).append(" ");
        while (!fin && i < length) {
          i++;
          name.append(commandArray[i].replace("\"", "")).append(" ");
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
      boolean add = ticket.ticketAdd(id, store, amount);
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
