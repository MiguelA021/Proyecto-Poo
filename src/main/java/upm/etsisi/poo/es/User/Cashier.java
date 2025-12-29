package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.Ticket.Ticket;

import java.util.ArrayList;
import java.util.Map;

public class Cashier extends User {
    private static final String UPM_WORKER = "UW";
    private static final String ID_ERROR = "The id given has been already used";
    private static final String ID_NOT_FOUND = "The id given, was not found ";
    private final ArrayList<Integer> ticketIds;

    public Cashier(String email, String name, String id) {
        this.ticketIds = new ArrayList<>();
        this.email = email;
        this.name = name;
        this.id = id;
        // tickets ya se inicializa en User, pero si lo quieres asegurar:
        // this.tickets = new TreeMap<>();
    }

    /**
     * List tickets belonging to this cashier ordered by id
     */
    public String listTickets() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Integer, Ticket> it : tickets.entrySet()) {
            Ticket t = it.getValue();
            str.append("  ")
                    .append(t.getId())
                    .append(" - ")
                    .append(t.getStatus().toString().toUpperCase())
                    .append("\n");
        }
        return str.toString();
    }

    /**
     * NEW (E3): Adds a ticket object already created (Customer or Enterprise)
     * @return ticket id, or -1 if invalid
     */
    public int addTicket(Ticket ticket) {
        if (ticket == null) {
            System.out.println("ERROR: ticket is null");
            return -1;
        }

        Integer id = ticket.getId();
        if (id == null) {
            // In your design tickets always have id, but just in case:
            id = (int) (Math.random() * 100000);
            while (tickets.containsKey(id)) {
                id = (int) (Math.random() * 100000);
            }
        }

        if (tickets.containsKey(id)) {
            System.out.println(ID_ERROR);
            return id;
        }

        tickets.put(id, ticket);
        return id;
    }

    /**
     * Returns ticket by id (polymorphic)
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
        return "Cash{identifier='" + UPM_WORKER + id + "', name='" + name + "', email='" + email + "'}";
    }

    /**
     * Remove ticket by id
     */
    public boolean removeTicket(int id) {
        if (tickets.containsKey(id)) {
            tickets.remove(id);
            return true;
        }
        return false;
    }

    public void insertTicket(int id) {
        ticketIds.add(id);
    }

    public boolean hasTicketId(int id) {
        return tickets.containsKey(id);
    }

}
