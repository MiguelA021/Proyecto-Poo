package upm.etsisi.poo.es.Tickets;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Product.ProductController;
import upm.etsisi.poo.es.Product.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EnterpriseServiceTicket extends Ticket {
    private final String INCORRECT = "Not a Service";

    private final List<Service> services = new ArrayList<>();

    public EnterpriseServiceTicket(Integer id) {
        super(id);
        this.productList = null;
    }

    public EnterpriseServiceTicket(Integer id, Status status) {
        super(id);
        this.productList = null;
        this.status = status;
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

    @Override
    public String print(boolean close) {

        if (close) {
            if(!this.close()) System.out.println(PERIOD_NOT_VALID);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Ticket : ").append(this.getId()).append("\n");

        for (Service s : this.getServices()) {
            sb.append("  ").append(s.toString()).append("\n");
        }

        return sb.toString();
    }

    public Service ticketRemove(int prodId) {
        Product product = ProductController.getInstance().getProduct(prodId);
        if (!(product instanceof Service)) {
            System.out.println(INCORRECT);
            return null;
        } else {
            this.services.remove((Service) product);
            return (Service) product;
        }

    }

    @Override
    public String toStringNew() {
        StringBuilder sc = new StringBuilder(); // Soy Aaron, lo de format() esta puesto para que siga el formato que
        // buscamos de fecha.
        // te lo pongo para que asi no te comas la cabeza con eso. Por lo demás ya te
        // dejo que sigas con ello
        sc.append(TICKET + " " + this.id + "\n");
        sc.append(TICKET_NEW_OK);
        return sc.toString();
    }

    @Override
    public void printCsv(CSVPrinter csvPrinter) throws Exception {
        csvPrinter.printRecord("EnterpriseServiceTicket", id, status);
        for (Service s : services) {
            csvPrinter.printRecord(id, "Service", s.getMaxUseDate(), s.getName(), s.getId());
        }
    }
}