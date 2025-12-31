package upm.etsisi.poo.es.Ticket;

import java.util.HashMap;

public class TicketData {
  private static final String ID_ERROR = "The id given has been already used";
  HashMap<Integer, Ticket> tickets;
  private static TicketData instance;

  private TicketData() {
    this.tickets = new HashMap<>();
  }

  public static TicketData getInstance() {
    if (instance == null) {
      instance = new TicketData();
    }
    return instance;
  }

  public Ticket getTicket(int ticketId) {
    Ticket resul = tickets.get(ticketId);
    if (resul == null) {
      System.out.println(ID_ERROR);
    }
    return resul;
  }

  public int addTicket() {
    int id;
    do {
      id = (int) (Math.random() * 100000);
    } while (tickets.containsKey(id));
    CustomerTicket customerTicket = new CustomerTicket(id);
    tickets.put(customerTicket.getId(), customerTicket);
    System.out.println(customerTicket.toStringNew());
    return id;
  }

  public boolean addTicket(int idTicket) {
    boolean resul = false;
    if (tickets.containsKey(idTicket))
      System.out.println(ID_ERROR);
    else {
      CustomerTicket customerTicket = new CustomerTicket(idTicket);
      tickets.put(idTicket, customerTicket);
      resul = true;
      System.out.println(customerTicket.toStringNew());
    }
    return resul;
  }
}
