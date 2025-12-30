package upm.etsisi.poo.es.Ticket;

import upm.etsisi.poo.es.Service.Service;
import upm.etsisi.poo.es.Status;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import upm.etsisi.poo.es.Ticket.print.EnterpriseServiceTicketPrinter;

public class EnterpriseServiceTicket extends Ticket {

    private final List<Service> services = new ArrayList<>();

    public EnterpriseServiceTicket(Integer id) {
        super(id, new EnterpriseServiceTicketPrinter());
    }

    @Override
    public boolean canBeClosed() {
        if (services.isEmpty()) {
            return false;
        }

        LocalDate today = LocalDate.now();
        for (Service s : services) {
            if (s.getMaxUseDate() != null && s.getMaxUseDate().isBefore(today)) {
                return false;
            }
        }
        return true;
    }

    public boolean addService(Service s) {
        if (s == null) return false;
        if (status == Status.CLOSED) return false;

        // Regla de inclusión por fecha máxima: si ya está caducado, no se añade
        LocalDate today = LocalDate.now();
        if (s.getMaxUseDate() != null && s.getMaxUseDate().isBefore(today)) return false;

        services.add(s);
        if (status == Status.EMPTY) status = Status.OPEN;
        return true;
    }

    public List<Service> getServices() {
        return new ArrayList<>(services);
    }
}
