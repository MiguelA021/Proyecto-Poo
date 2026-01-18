package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Tickets.*;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;

import java.util.HashMap;

public class TicketCommand implements Command {
  private TicketController ticketController;

  public TicketCommand() {
    this.ticketController = TicketController.getInstance();
  }

  @Override
  public String getName() {
    return "ticket";
  }

  @Override
  public boolean execute(String fullLine, String[] args) {
    if (args.length < 2) {
      System.out.println(INCORRECT);
      return false;
    }

    String sub = args[1];

    switch (sub) {
      case "add":
        ticketAdd(args);
        break;
      case "remove":
        ticketRemove(args);
        break;
      case "print":
        ticketPrint(args);
        break;
      case "list":
        ticketList();
        break;
      case "new":
        ticketController.ticketNew(args);
        break;
      default:
        System.out.println(INCORRECT);
        break;
    }

    return false;
  }

  private void ticketAdd(String[] args) {
    if (args.length < 4) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      int ticketId = Integer.parseInt(args[2]);
      int casherId = Integer.parseInt(args[3].replace("UW", ""));

      if (CashierController.getInstance().
              exitsTicket(casherId, ticketId)) {
        ticketController.prodAdd(args);
      }
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketRemove(String[] args) {
    if (args.length != 5) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      int ticketId = Integer.parseInt(args[2]);
      int prodId = Integer.parseInt(args[4]);
      int casherId = Integer.parseInt(args[3].replace("UW", ""));

      if (CashierController.getInstance().exitsTicket(casherId, ticketId)) {
        ticketController.ticketRemove(ticketId, prodId);
      } else {
        System.out.println("ticket does not exist");
      }
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketPrint(String[] args) {
    if (args.length < 4) {
      System.out.println(INCORRECT);
      return;
    }
    int ticketId = Integer.parseInt(args[2]);
    String casherId = args[3];
    int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
    Cashier cashier = CashierController.getInstance().searchCasherById(casherIdGood);

      Ticket ticket = TicketData.getInstance().getTicket(ticketId);
    if(cashier == null && ticket == null){
        System.out.println("Cashier and ticket don't exist");
    }else if(cashier == null){
        System.out.println("Cashier doesn't exist");
        return;
    }else if(ticket == null){
        System.out.println("Ticket doesn't exist");
        return;
    }
    if (CashierController.getInstance().exitsTicket(casherIdGood, ticketId)) {
        ticketController.ticketPrint(ticketId);
    }else{
        System.out.println("This ticket does not belong to cashier");
    }
  }

  private void ticketList() {
    CashierController cashierController = CashierController.getInstance();
    HashMap<Integer, Cashier> cashiers = cashierController.getMap();
    System.out.println("Ticket List:");
    for (Integer id : cashiers.keySet()) {
      cashierController.listTicketsOnCasher(id);
    }
    System.out.println("ticket list: ok");
  }

}
