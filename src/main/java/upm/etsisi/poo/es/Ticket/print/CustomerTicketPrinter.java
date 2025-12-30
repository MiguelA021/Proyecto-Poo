package upm.etsisi.poo.es.Ticket.print;

import upm.etsisi.poo.es.CustomerTicket;
import upm.etsisi.poo.es.Ticket.Ticket;


public class CustomerTicketPrinter implements TicketPrinter {

    @Override
    public String print(Ticket ticket, boolean close) {

        CustomerTicket ct = (CustomerTicket) ticket;
        return ct.ticketPrint(close);
    }
}
