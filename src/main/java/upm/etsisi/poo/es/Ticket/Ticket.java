package upm.etsisi.poo.es.Ticket;

import upm.etsisi.poo.es.Status;
import java.io.Serializable;
import upm.etsisi.poo.es.Ticket.print.TicketPrinter;


public abstract class Ticket implements Serializable {

    protected final Integer id;
    protected final TicketPrinter printer;
    protected Status status;

    protected Ticket(Integer id, TicketPrinter printer) {
        this.id = id;
        this.printer = printer;
        this.status = Status.EMPTY;
    }

    public Integer getId() { return id; }
    public Status getStatus() { return status; }
    public TicketPrinter getPrinter() { return printer; }

    public abstract boolean canBeClosed();

    public boolean close() {
        if (status == Status.CLOSED) {
            return false;
        }
        if (!canBeClosed()){
            return false;
        }

        status = Status.CLOSED;
        return true;
    }
}
