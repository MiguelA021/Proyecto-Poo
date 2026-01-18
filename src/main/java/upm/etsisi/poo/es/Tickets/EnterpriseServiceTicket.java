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

    /**
     * The method checks if the Ticket can be closed, it must not be empty and if it has services, the services must be on a valid date
     *
     * @return returns true if it can be closed, else returns false
     */
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

    /**
     * The method adds a service into the ticket if the ticket is not closed and the dates are valid
     *
     * @param s the service that we are going to add
     * @return returns true if the service has been added successfully, else returns false
     */
    public boolean addService(Service s) {
        if (s == null) return false;
        if (status == Status.CLOSE) return false;
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

    /**
     * The method creates a String with a specific format
     *
     * @param close shows if the ticket is closed or not
     * @return the String ready to print
     */
    @Override
    public String print(boolean close) {

        if (close) {
            if (!this.close())
                return PERIOD_NOT_VALID;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(TICKET).append(this.toStringId());
        sb.append("\nServices Included:");
        for (Service s : this.getServices()) {
            sb.append("\n  ").append(s.toString());
        }

        return sb.toString();
    }

    /**
     * The method removes the product from the ticket
     *
     * @param prodId the id of the product we want to remove
     * @return if it has been removed successfully returns the product, else returns null
     */
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

    /**
     * The method creates a String with a specific format
     *
     * @return the String ready to print
     */
    @Override
    public String toStringNew(boolean withId) {
        StringBuilder sc = new StringBuilder();
        sc.append(TICKET + this.id + "\n");
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