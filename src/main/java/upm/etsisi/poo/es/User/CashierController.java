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

  /**
   * The method returns the unique instance of the class
   * @return the instance of the class
   */
  public static CashierController getInstance() {
    if (instance == null) {
      instance = new CashierController();
    }
    return instance;
  }

  /**
   * The method returns the HashMap of the cashiers
   * @return the HashMap of cashiers
   */
  public HashMap<Integer, Cashier> getMap() {
    return this.cashers;
  }

  /**
   * The method adds the cashier if the id has not been already used
   * @param id The id given by parameter, if null it generates one randomly
   * @param name the name given by parameter
   * @param email the email given by parameter
   * @return the method returns true if the cashier has been added successfully, else return false
   */
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

  /**
   * The method list the cashiers ordered by their name
   */
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

  /**
   * The method turns the tree into a list ordered by the name of the cashiers
   * @return the list already ordered
   */
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

  /**
   * The method adds the ticket to the cashier
   * @param ticketId the id of the ticket we want to add
   * @param cashierId the id of the cashiers that we are going to add the ticket
   */
  public void addTicket(int ticketId, int cashierId) {
    Cashier cashier = cashers.get(cashierId);
    cashier.addTicket(ticketId);
  }

  /**
   * The method returns the Cashier with the id given
   * @param id the id given by parameter
   * @return it returns the cashier with the id given if it has been found, else returns null
   */
  public Cashier searchCasherById(int id) {
      if(cashers.containsKey(id)) {
          return cashers.get(id);
      }else return null;
  }

  /**
   * The method removes the cashier with the id given
   * @param id the id given by parameter
   * @return the method returns true if the cashier has been removed successfully
   */
  public boolean removeCasher(int id) {
    return (cashers.remove(id) != null);
  }

  /**
   * The method searches if the cashier has the ticket linked
   * @param cashId the id of the cashier given by parameter
   * @param idTicket the id of the ticket given by parameter
   * @return returns true if the ticket exists on the cashier, else return false
   */
  public boolean exitsTicket(int cashId, int idTicket) {
    boolean exists = false;
    if (cashers.get(cashId).getTicketById(idTicket)) {
      exists = true;
    }
    return exists;
  }

}
