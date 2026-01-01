package upm.etsisi.poo.es.Tickets;

import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Product.ProductController;
import upm.etsisi.poo.es.Product.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean addProduct(Product p, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSED) {
            int before = this.amount;
            if (p == null) {
                resul = false;
                System.out.println(ERROR_PRODUCT_ID_NOT_FOUND);

            } else {
                if (this.amount == 0) {
                    this.status = Status.OPEN;
                }

                if (p instanceof Event) {
                    Event event = (Event) p;
                    if (event.fechaValida(LocalDateTime.now())) {
                        if (amount <= event.getMaxPersonas()) {

                            double price = event.getPricePerPerson() * amount;
                            event.setPrice(price);
                            products.add(event);
                            System.out.println(print(false));
                            System.out.println(ADD_OK);

                        } else {
                            System.out.println(MANY_PEOPLE);
                            resul = false;
                        }
                    } else {
                        System.out.println(PERIOD_NOT_VALID);
                    }

                } else {
                    for (int i = 0; i < amount; i++) {
                        products.add(p);
                    }
                    System.out.println(print(false));
                    if ((this.amount - before) == amount) {
                        resul = true;
                        System.out.println(ADD_OK);
                    } else {
                        resul = false;
                        System.out.println(ERROR_FULL);
                    }

                }

            }
        } else {
            resul = false;
            System.out.println("ERROR: the ticket is closed. It can't be modified");
        }
        return resul;
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
        sb.append("Services Included:\n");
        // Servicios: sin precio
        for (Service s : this.getServices()) {
            sb.append("  ").append(s.toString()).append("\n");
        }
        if (!products.isEmpty()) {
            sb.append("Products Included:\n");
            // Productos: con precio, aplicando descuento extra (15% por servicio)
            double totalPrice = 0.0;
            for (Product p : this.getProducts()) {
                totalPrice += p.getPrice();
                sb.append("  ").append(p.toString());
            }

            double extraRate = this.getExtraDiscountRate();
            double extraDiscount = totalPrice * extraRate;
            double finalPrice = totalPrice - extraDiscount;

            sb.append("  Total price: ").append(String.format(Locale.US, "%.3f", totalPrice)).append("\n");
            sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", extraDiscount)).append("\n");
            sb.append("  Final price: ").append(String.format(Locale.US, "%.3f", finalPrice));
        }
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

}