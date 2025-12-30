package upm.etsisi.poo.es.Ticket;

import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Service.Service;
import upm.etsisi.poo.es.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import upm.etsisi.poo.es.Ticket.print.EnterpriseMixedTicketPrinter;

public class EnterpriseMixedTicket extends Ticket {

    private final List<Product> products = new ArrayList<>();
    private final List<Service> services = new ArrayList<>();

    public EnterpriseMixedTicket(Integer id) {
        super(id, new EnterpriseMixedTicketPrinter());
    }

    @Override
    public boolean canBeClosed() {

        //Tiene al menos un producto y un servicio

        if (products.isEmpty() || services.isEmpty()){
            return false;
        }

        //Todos los servicios tienen que tener una fecha válida

        LocalDate today = LocalDate.now();
        for (Service s : services) {
            if (s.getMaxUseDate() != null && s.getMaxUseDate().isBefore(today)) {
                return false;
            }
        }
        return true;
    }

    public double getExtraDiscountRate() {
        double rate = services.size() * 0.15;

        /* Sirve de tope por si se pasa de descuentos si quereis lo meteis o no si lo veis absurdo

        if (rate > 1.0) {
            rate = 1.0;
        }

         */
        return rate;
    }

    public boolean addProduct(Product p) {
        if (p == null){
            return false;
        }

        if (status == Status.CLOSED){
            return false;
        }

        products.add(p);
        if (status == Status.EMPTY){
            status = Status.OPEN;
        }
        return true;
    }

    public boolean addService(Service s) {
        if (s == null){
            return false;
        }
        if (status == Status.CLOSED){
            return false;
        }

        //No valen servicios con fechas caducadas

        LocalDate today = LocalDate.now();
        if (s.getMaxUseDate() != null && s.getMaxUseDate().isBefore(today)) return false;

        services.add(s);
        if (status == Status.EMPTY) status = Status.OPEN;
        return true;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<Service> getServices() {
        return new ArrayList<>(services);
    }
}
