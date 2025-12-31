package upm.etsisi.poo.es.Ticket;

import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Product.ProductController;
import upm.etsisi.poo.es.Product.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EnterpriseMixedTicket extends Ticket {

    private final List<Product> products = new ArrayList<>();
    private final List<Service> services = new ArrayList<>();

    public EnterpriseMixedTicket(Integer id) {
        super(id);
    }

    @Override
    public boolean canBeClosed() {

        //Tiene al menos un producto y un servicio

        if (products.isEmpty() || services.isEmpty()) {
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
        if (p == null) {
            return false;
        }

        if (status == Status.CLOSED) {
            return false;
        }

        products.add(p);
        if (status == Status.EMPTY) {
            status = Status.OPEN;
        }
        return true;
    }

    public boolean addService(Service s) {
        if (s == null) {
            return false;
        }
        if (status == Status.CLOSED) {
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

    @Override
    public String print(boolean close) {

        if (close) this.close(); // solo cerrará si hay >=1 producto y >=1 servicio

        StringBuilder sb = new StringBuilder();
        sb.append("Ticket : ").append(this.getId()).append("\n");

        // Servicios: sin precio
        for (Service s : this.getServices()) {
            sb.append("  ").append(s.toString()).append("\n");
        }

        // Productos: con precio, aplicando descuento extra (15% por servicio)
        double totalPrice = 0.0;
        for (Product p : this.getProducts()) {
            if (p != null) totalPrice += p.getPrice();
        }

        double extraRate = this.getExtraDiscountRate();
        double extraDiscount = totalPrice * extraRate;
        double finalPrice = totalPrice - extraDiscount;

        for (Product p : this.getProducts()) {
            if (p != null) {
                sb.append("  ").append(p.toString()).append("\n");
            }
        }

        sb.append("  Total price: ").append(String.format(Locale.US, "%.3f", totalPrice)).append("\n");
        sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", extraDiscount)).append("\n");
        sb.append("  Final price: ").append(String.format(Locale.US, "%.3f", finalPrice));

        return sb.toString();
    }

    public Product ticketRemove(int prodId) {
        Product product = ProductController.getInstance().getProduct(prodId);
        if (product instanceof Service) {
            if (services.contains(product)) {
                services.remove(product);
                return product;
            } else {
                return null;
            }
        } else {
            if (products.contains(product)) {
                products.remove(product);
                return product;
            } else {
                return null;
            }
        }
    }
}