package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;
import java.util.Arrays;

public class TicketCommand implements Command {

  @Override
  public String getName() {
    return "ticket";
  }

  @Override
  public String getDescription() {
    return "ticket add|remove|print ...  - ticket management";
  }

  @Override
  public boolean execute(String fullLine, String[] args, Store store, Ticket ticket) {
    if (args.length < 2) {
      System.out.println(INCORRECT);
      return false;
    }

    String sub = args[1];

    switch (sub) {
      case "add":
        ticketAdd(args, store, ticket);
        break;
      case "remove":
        ticketRemove(args, ticket);
        break;
      case "print":
        ticketPrint(ticket);
        break;
      default:
        System.out.println(INCORRECT);
    }

    return false;
  }

  private void ticketAdd(String[] args, Store store, Ticket ticket) {
  /*  if (args.length != 4) {
      System.out.println(INCORRECT);
      return;
    }*/
    try {
      int id = Integer.parseInt(args[2]); // seria el 4 ?
      int amount = Integer.parseInt(args[3]);// seria el 5?
        if(args.length > 4){
            String [] personalizations = Arrays.copyOfRange(args, 5, args.length);
            ticket.ticketAddP(id, store, amount,personalizations);
        }else{
            ticket.ticketAdd(id, store, amount);
        }

      // ticketAdd ya imprime y dice "ticket add: ok" o errores
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketRemove(String[] args, Ticket ticket) {
    if (args.length != 3) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      int id = Integer.parseInt(args[2]);
      Product product = ticket.ticketRemove(id);
      if (product == null) {
        System.out.println(NOTEXIST);
      } else {
        System.out.println(product.toString());
        System.out.println("ticket remove: ok");
      }
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketPrint(Ticket ticket) {
    String printed = ticket.ticketPrint(true);
    if (printed.isEmpty()) {
      System.out.println(EMPTY_TICKET);
    } else {
      System.out.println(printed);
      System.out.println("ticket print: ok");
    }
  }
}
