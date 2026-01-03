package upm.etsisi.poo.es.User;

import java.util.ArrayList;

public class Customer extends User {
  protected final int cashierId;
  private ArrayList<Integer> tickets ;
  public Customer(String email, String name, String id, int cashierId) {

    this.email = email;
    this.name = name;
    this.cashierId = cashierId;
    this.id = id;
    this.tickets = new ArrayList<Integer>();
  }

  public String toString() {
    return "Client{identifier='" + id + "', name='" + name + "', email='" + email + "', cash=UW" + cashierId + "}";
  }

  /**
   * The method adds the ticket into the tickets associated with the customer
   * 
   * @param id     the id of the ticket
   */
  public void addTicket(Integer id) {
    this.tickets.add(id);
  }

}
