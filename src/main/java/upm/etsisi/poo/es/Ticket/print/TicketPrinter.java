package upm.etsisi.poo.es.Ticket.print;

import upm.etsisi.poo.es.Ticket.Ticket;

public interface TicketPrinter {
    String print(Ticket ticket, boolean close);
}
