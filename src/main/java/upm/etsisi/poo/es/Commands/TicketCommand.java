package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.type;

public class TicketCommand extends Command {

  public TicketCommand(String command) {
    super(command);
  }
}

class CommandTicketAdd extends TicketCommand {

  public CommandTicketAdd(String command) {
    super(command);
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
}

class CommandTicketRemove extends TicketCommand {
  public CommandTicketRemove(String command) {
    super(command);
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

}

class CommandTicketPrint extends TicketCommand {

  public CommandTicketPrint(String command) {
    super(command);
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

}
