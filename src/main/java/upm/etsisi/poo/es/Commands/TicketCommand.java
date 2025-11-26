package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;
import java.util.Arrays;

import java.util.ArrayList;

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
  public boolean execute(String fullLine, String[] args, Store store) {
    if (args.length < 2) {
      System.out.println(INCORRECT);
      return false;
    }

    String sub = args[1];

    switch (sub) {
      case "add":
        ticketAdd(args, store);
        break;
      case "remove":
        ticketRemove(args,store);
        break;
      case "print":
        ticketPrint(args,store);
        break;
      case "new":
        ticketNew(args, store);
      default:
        System.out.println(INCORRECT);
    }

    return false;
  }

  private void ticketAdd(String[] args, Store store) {
    if (args.length != 4) {
      System.out.println(INCORRECT);
      return;
    }*/
    try {
      int ticketId = Integer.parseInt(args[2]);
      String casherId = args[3];
      String casherIdGood = "";
      int prodId = Integer.parseInt(args[4]);
      int ammount = Integer.parseInt(args[5]);
      for (int i = 2; i < casherId.length(); i++) {
        casherIdGood.concat(Character.toString(casherId.charAt(i)));
      }
      Cashier cashier = store.searchCasherById(Integer.parseInt(casherIdGood));
      Ticket ticket = cashier.getTicketById(ticketId);
      if(args.length > 5){
        ArrayList<String> personalizaciones = new ArrayList<String>();
        for (int i = 5; i < args.length; i++) {
          String personalizacion = args[i].replaceAll("--p", "");
          personalizaciones.add(personalizacion);
        }
        ticket.ticketAdd(prodId, store, ammount, personalizaciones);
      }

      ticket.ticketAdd(prodId, store, ammount, null);

      // ticketAdd ya imprime y dice "ticket add: ok" o errores
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketRemove(String[] args, Store store) {
    if (args.length != 3) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      int ticketId = Integer.parseInt(args[2]);
      int prodId = Integer.parseInt(args[4]);
      String casherId = args[3];
      String casherIdGood = "";
      for (int i = 2; i < casherId.length(); i++) {
        casherIdGood.concat(Character.toString(casherId.charAt(i)));
      }
      Cashier cashier = store.searchCasherById(Integer.parseInt(casherIdGood));
      Ticket ticket = cashier.getTicketById(ticketId);
      Product product = ticket.ticketRemove(prodId);
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

  private void ticketPrint(String[] args, Store store) {
    int ticketId = Integer.parseInt(args[2]);
    String casherId = args[3];
    String casherIdGood = "";
    for (int i = 2; i < casherId.length(); i++) {
      casherIdGood.concat(Character.toString(casherId.charAt(i)));
    }
    Cashier cashier = store.searchCasherById(Integer.parseInt(casherIdGood));
    Ticket ticket = cashier.getTicketById(ticketId);
    String printed = ticket.ticketPrint(true);
    if (printed.isEmpty()) {
      System.out.println(EMPTY_TICKET);
    } else {
      System.out.println(printed);
      System.out.println("ticket print: ok");
    }
  }

  private void ticketNew(String[] args, Store store) {
    if (args.length == 5) {
      String casherId = args[3];
      String casherIdGood = "";
      for (int i = 2; i < casherId.length(); i++) {
        casherIdGood.concat(Character.toString(casherId.charAt(i)));
      }
      int userId = store.dniToId(args[4]);
      int id = Integer.parseInt(args[2]);
      store.addTicketOnCasher(id, Integer.parseInt(casherIdGood), userId);
    } else if (args.length == 4) {
      String casherId = args[2];
      String casherIdGood = "";
      for (int i = 2; i < casherId.length(); i++) {
        casherIdGood.concat(Character.toString(casherId.charAt(i)));
      }
      int userId = store.dniToId(args[3]);
      store.addTicketOnCasher(null, Integer.parseInt(casherIdGood), userId);
    } else {
      System.out.println(INCORRECT);
    }

  }
}
