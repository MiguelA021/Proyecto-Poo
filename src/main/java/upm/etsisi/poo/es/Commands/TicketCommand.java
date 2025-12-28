package upm.etsisi.poo.es.Commands;


import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.TicketController;
import upm.etsisi.poo.es.TicketData;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;
import upm.etsisi.poo.es.User.CustomerController;

import java.util.HashMap;
import java.util.Map;

public class TicketCommand implements Command {
  private TicketController ticketController;
  private CashierController cashC;
  private TicketData ticketData;

  public TicketCommand(){
    this.ticketController = new TicketController();
    cashC = CashierController.getInstance();
    ticketData = TicketData.getInstance();
  }

  @Override
  public String getName() {
    return "ticket";
  }

  @Override
  public String getDescription() {
    return "ticket add|remove|print ...  - ticket management";
  }

  @Override
  public boolean execute(String fullLine, String[] args) {
      Store store = Store.getInstance();
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
        ticketNew(args);
        break;
      default:
        System.out.println(INCORRECT);
        break;
    }

    return false;
  }
  private void ticketList(){
    CashierController custC = CashierController.getInstance();
    HashMap<Integer, Cashier> cashers = custC.getMap();
    System.out.println("Ticket list: ");

    for (Map.Entry<Integer, Cashier> entry : cashers.entrySet()) {
      Cashier casher = entry.getValue();
      System.out.print(casher.listTickets());
    }
    System.out.println("ticket list: ok");
  }

  private void ticketAdd(String[] args) {
    if (args.length < 6) {
      System.out.println(INCORRECT);
      return;
    }
    try {

      int ticketId = Integer.parseInt(args[2]);
      String casherId = args[3];
      int casherIdGood = Integer.parseInt(casherId.replace("UW", ""));
      if (cashC.exitsTicket( casherIdGood, ticketId)){
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
      String casherId = args[3];
      int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
      if(cashC.exitsTicket(casherIdGood,ticketId))  ticketController.ticketRemove(ticketId,prodId);
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }

  private void ticketPrint(String[] args) {
    int ticketId = Integer.parseInt(args[2]);
    String casherId = args[3];
    int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
    if(cashC.exitsTicket(casherIdGood,ticketId)) ticketController.ticketPrint(ticketId);
  }

  private void ticketNew(String[] args) {
    if (args.length != 4 && args.length != 5) {
      System.out.println(INCORRECT);
      return;
    }

    Integer ticketId ;
    int cashId;
    int userId;

    try {

      String cashierId = args[2].replaceAll("UW", "");
      cashId = Integer.parseInt(cashierId);
      userId = CustomerController.getInstance().dniToId(args[3]);
      if (args.length == 4) {
        // ticket new <cashId> <userId>
        ticketId = ticketData.addTicket();
        cashC.addTicket( ticketId,cashId);
        CustomerController.getInstance().addTicket(ticketId,userId);
      } else {
        // ticket new <id> <cashId> <userId>
        ticketId = Integer.valueOf(args[2]);
        if( !ticketData.addTicket(ticketId)) System.out.println(ID_REPEAT);
        else{
          CustomerController.getInstance().addTicket(ticketId,userId);
        }
      }

    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }

  }
}
