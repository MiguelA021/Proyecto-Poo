package upm.etsisi.poo.es.Ticket.print;

import upm.etsisi.poo.es.Service.Service;
import upm.etsisi.poo.es.Ticket.EnterpriseServiceTicket;
import upm.etsisi.poo.es.Ticket.Ticket;

public class EnterpriseServiceTicketPrinter implements TicketPrinter {

    @Override
    public String print(Ticket ticket, boolean close) {
        EnterpriseServiceTicket t = (EnterpriseServiceTicket) ticket;

        if (close){
            t.close();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Ticket : ").append(t.getId()).append("\n");

        for (Service s : t.getServices()) {
            sb.append("  ").append(s.toString()).append("\n");
        }

        return sb.toString();
    }
}
