package upm.etsisi.poo.es.Tickets;

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
    public String print( boolean close) {

        if (close) {
            this.close();
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
        if(!(product instanceof Service)){
            System.out.println(INCORRECT);
            return null;
        }else{
            this.services.remove((Service) product);
            return (Service) product;
        }

    }
}