package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.TicketData;

import java.util.ArrayList;

public class Cashier extends User {
  private static final String UPM_WORKER = "UW";
  private static final String ID_ERROR = "The id given has been already used";
  private static final String ID_NOT_FOUND = "The id given, was not found ";

  public Cashier(String email, String name, String id) {
    this.tickets = new ArrayList<Integer>();
    this.email = email;
    this.name = name;
    this.id = id;
  }

  /**
   * The method runs through the tree and gives back the pair [K,V] ordered by the
   * key
   *
   * @return The string returned is the list of tickets that belongs to the atm
   *         ordered by their id
   */
  public String listTickets() {
    StringBuilder str = new StringBuilder();
    tickets.sort(Integer::compareTo);
    for (Integer id : tickets) {
      Ticket ticket = TicketData.getInstance().getTicket(id);
      str.append(ticket.formatList()).append("\n");
    }
    return str.toString();
  }

  /**
   * The method adds the ticket given by id into the cashers tree
   *
   * @param id the id given by parameter (if it is not given, it generates one
   *           automatically)
   */
  public int addTicket(Integer id) {
    if (id == null) {
      do {
        id = (int) (Math.random() * 100000);
      } while (tickets.contains(id));
    }
    if (tickets.contains(id)) {
      System.out.println(ID_ERROR);
    } else {
      tickets.add(id);
    }
    return id;
  }

  /**
   * .
   * The method returns the ticket given by id
   *
   * @param id the id of the ticket
   * @return the ticket (if it has been found)
   */
  public boolean getTicketById(int id) {
    boolean ticket = false;
    if (tickets.contains(id)) {
      ticket = true;
    } else {
      System.out.println(ID_NOT_FOUND);
    }
    return ticket;

  }

  public String toString() {
    return "Cash{identifier='" + UPM_WORKER + id + "', name='" + name + "', email='" + email + "'}";
  }

  /**
   * The method removes the ticket given by the id
   *
   * @param id the id of the ticket
   * @return returns true if the ticket has been removed
   */
  public boolean removeTicket(int id) {
    boolean resul = false;
    if (tickets.contains(id)) {
      resul = true;
      tickets.remove(id);
    }
    return resul;
  }

  public void insertTicket(int id) {
    tickets.add(id);
  }

}
