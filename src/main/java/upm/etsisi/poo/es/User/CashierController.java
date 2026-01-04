package upm.etsisi.poo.es.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CashierController {
  private static CashierController instance;
  public static final String ID_ERROR = "The id given has already been  used";
  HashMap<Integer, Cashier> cashers;

  private CashierController() {
    this.cashers = new HashMap<Integer, Cashier>();
  }

  public static CashierController getInstance() {
    if (instance == null) {
      instance = new CashierController();
    }
    return instance;
  }

  public HashMap<Integer, Cashier> getMap() {
    return this.cashers;
  }

  public boolean addCasher(Integer id, String name, String email) {
    boolean resul = true;
    if (id == null) {
      do {
        id = (int) (Math.random() * 10000000);
      } while (cashers.containsKey(id));
    }
    if (!cashers.containsKey(id)) {
      Cashier cashier = new Cashier(email, name, id.toString());
      cashers.put(id, cashier);
      System.out.println(cashier.toString());
      System.out.println("cash add: ok");
    } else {
      System.out.println(ID_ERROR);
      resul = false;
    }
    return resul;
  }

  public void listCashers() {
    ArrayList<Cashier> listSort = cashersToList();
    if (!listSort.isEmpty()) {
      System.out.println("Cash:");
      for (Cashier cashier : listSort) {
        System.out.println("  " + cashier.toString());
      }
      System.out.println("cash list: ok");
    } else {
      System.out.println("no cashiers in store");
    }

  }

  private ArrayList<Cashier> cashersToList() {
    ArrayList<Cashier> resul = new ArrayList<>(cashers.values());
    resul.sort(Comparator.comparing(Cashier::getName));
    return resul;
  }

  public void listTicketsOnCasher(int id) {
    if (cashers.containsKey(id)) {
      System.out.print(cashers.get(id).listTickets());
    } else {
      System.out.println(ID_ERROR);
    }
  }


  public void addTicket(int ticketId, int cashierId) {
    Cashier cashier = cashers.get(cashierId);
    cashier.addTicket(ticketId);
  }

  public Cashier searchCasherById(int id) {
      if(cashers.containsKey(id)) {
          return cashers.get(id);
      }else return null;
  }

  public boolean removeCasher(int id) {
    return (cashers.remove(id) != null);
  }

  public boolean exitsTicket(int cashId, int idTicket) {
    boolean exists = false;
    if (cashers.get(cashId).getTicketById(idTicket)) {
      exists = true;
    }
    return exists;
  }

}
