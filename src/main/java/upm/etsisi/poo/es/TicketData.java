package upm.etsisi.poo.es;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class TicketData {
    private static final String ID_ERROR = "The id given has been already used";
    private static final String ID_NOT_FOUND = "The id given, was not found ";
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
        Ticket resul =tickets.get(ticketId);
        if(resul == null){
            System.out.println(ID_ERROR);
        }
        return null;
    }

    public int addTicket() {
        int  id ;
        do {
            id=(int)(Math.random()*100000);
        } while (tickets.containsKey(id));
        Ticket ticket = new Ticket(id);
        tickets.put(ticket.getId(), ticket);
        return id;
    }

    public boolean addTicket(int idTicket) {
        boolean resul = false;
        if(tickets.containsKey(idTicket)) System.out.println(ID_ERROR);
        else{
            Ticket ticket = new Ticket(idTicket);
            tickets.put(ticket.getId(),ticket);
            resul = true;
        }
        return resul;
    }
}
