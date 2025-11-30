package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.Ticket;
import java.util.Map;
import java.util.TreeMap;

public class Cashier extends User {
  private static final String UPM_WORKER = "UW";
  private static final String ID_ERROR = "The id given has been already used";
  private static final String ID_NOT_FOUND = "The id given, was not found ";

  public Cashier(String email, String name, String id) {
    this.email = email;
    this.name = name;
    this.id = id;
    tickets = new TreeMap<>();
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
    for (Map.Entry<Integer, Ticket> it : tickets.entrySet()) {// saca para cada nodo del arbol (K,V) ordenado por la
      Ticket ticket = it.getValue();
      str.append(ticket.formatList()).append( "\n");
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
    if (id==null) {
      do {
        id=(int)(Math.random()*100000);
      } while (tickets.containsKey(id));
    }
    if (tickets.containsKey(id)) {
      System.out.println(ID_ERROR);
    } else {
      Ticket ticket = new Ticket(id);
      tickets.put(ticket.getId(), ticket);
    }
    return id;
  }

  /**.
   * The method returns the ticket given by id
   * 
   * @param id the id of the ticket
   * @return the ticket (if it has been found)
   */
  public Ticket getTicketById(int id) {
    Ticket ticket = null;
    if (tickets.containsKey(id)) {
      ticket = tickets.get(id);
    } else {
      System.out.println(ID_NOT_FOUND);
    }
    return ticket;
  }

  public String toString() {
    return "Cash{identifier='"+UPM_WORKER+id+"', name='"+name+"', email='"+email+"'}";
  }

  /**
   * The method removes the ticket given by the id
   * 
   * @param id the id of the ticket
   * @return returns true if the ticket has been removed
   */
  public boolean removeTicket(int id) {
    boolean resul = false;
    if (tickets.containsKey(id)) {
      resul = true;
      tickets.remove(id);
    }
    return resul;
  }

}
