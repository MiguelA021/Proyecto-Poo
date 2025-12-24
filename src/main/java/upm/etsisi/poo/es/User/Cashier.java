package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.Ticket;
import java.util.Map;
import java.util.TreeMap;

public class Cashier extends User implements ICashier {

  public Cashier(String email, String name, String id) {
    this.email = email;
    this.name = name;
    this.id = id;
    tickets = new TreeMap<>();
  }
  @Override
  public String listTickets() {
    StringBuilder str = new StringBuilder();
    for (Map.Entry<Integer, Ticket> it : tickets.entrySet()) {// saca para cada nodo del arbol (K,V) ordenado por la
      Ticket ticket = it.getValue();
      str.append(ticket.formatList()).append( "\n");
    }
    return str.toString();
  }
  @Override
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

  @Override
  public Ticket getTicketById(int id) {
    Ticket ticket = null;
    if (tickets.containsKey(id)) {
      ticket = tickets.get(id);
    } else {
      System.out.println(ID_NOT_FOUND);
    }
    return ticket;
  }
  @Override
  public String toString() {
    return "Cash{identifier='"+UPM_WORKER+id+"', name='"+name+"', email='"+email+"'}";
  }

  @Override
  public boolean removeTicket(int id) {
    boolean resul = false;
    if (tickets.containsKey(id)) {
      resul = true;
      tickets.remove(id);
    }
    return resul;
  }
}
