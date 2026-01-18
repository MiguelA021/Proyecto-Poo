package upm.etsisi.poo.es.Tickets;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.Product.*;

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

    public EnterpriseMixedTicket(Integer id, Status status) {
        super(id);
        this.status = status;
    }

    @Override
    public boolean close(){

        if (status == Status.CLOSE) {
            return true;
        }
        if(!this.canBeClosed()){
            return false;
        }
        status = Status.CLOSE;
       for (Product p : products){
           if(p instanceof BasicProduct){
               Product copyProduct =copy((BasicProduct) products.remove(p.getId()));
               products.add(copyProduct);

           }
       }

        return true;    }

    /**
     * The method checks if the Ticket can be closed, it must have at least either a product or a service.
     * And if it has services, all of them must be on a valid date
     */
    @Override
    public boolean canBeClosed() {
        if (products.isEmpty() || services.isEmpty()) {
            return false;
        }
        LocalDateTime today = LocalDateTime.now();
        for (Service s : services) {
            if (s.getMaxUseDate() != null && s.getMaxUseDate().isBefore(today.toLocalDate())) {
                return false;
            }
            for (Product product : products) {
                if (product instanceof Event) {
                    Event event = (Event) product;
                    if (!event.fechaValida(today)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * The method returns the discount applied to the services
     * @return the discount yet to apply
     */
    public double getExtraDiscountRate() {
        double rate = services.size() * 0.15;
        if (rate > 1.0) {//Se puede quitar, ya que topa el descuento
            rate = 1.0;
        }
        return rate;
    }

    /**
     * The method adds the product into the ticket
     *
     * @param p      The product we are adding
     * @param amount if it's an event, it shows the amount of people are coming to it. If it's just a product it shows the amount
     * @return returns true if the product has been added successfully, else returns false
     */
    public boolean addProduct(Product p, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSE) {
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
                            if (event instanceof Meeting) {
                                Meeting meeting = new Meeting(event.getId(), event.getName(), event.getPrice(), event.getExpiryDate().toLocalDate().toString());
                                meeting.setPrice(price);
                                products.add(meeting);
                            } else {
                                Food food = new Food(event.getId(), event.getName(), event.getPrice(), event.getExpiryDate().toLocalDate().toString());
                                food.setPrice(price);
                                products.add(food);
                            }
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


    public boolean addProductNoString(Product p, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSE) {
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
                            if (event instanceof Meeting) {
                                Meeting meeting = new Meeting(event.getId(), event.getName(), event.getPrice(), event.getExpiryDate().toLocalDate().toString());
                                meeting.setPrice(price);
                                products.add(meeting);
                            } else {
                                Food food = new Food(event.getId(), event.getName(), event.getPrice(), event.getExpiryDate().toLocalDate().toString());
                                food.setPrice(price);
                                products.add(food);
                            }

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
                    if ((this.amount - before) == amount) {
                        resul = true;
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

    /**
     * The method adds the service if the dates are correct
     *
     * @param s the service given by parameter
     * @return returns true if the service has been added successfully
     */
    public boolean addService(Service s) {
        if (s == null) {
            return false;
        }
        if (status == Status.CLOSE) {
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

    /**
     * The method prints the ticket
     *
     * @param close shows if the ticket is closed or not
     * @return the String ready to print
     */
    @Override
    public String print(boolean close) {

        if (close) if (!this.close())
            return PERIOD_NOT_VALID; // solo cerrará si hay >=1 producto y >=1 servicio

        StringBuilder sb = new StringBuilder();
        sb.append(TICKET).append(this.toStringId());
        sb.append("\nServices Included:");
        // Servicios: sin precio
        for (Service s : this.getServices()) {
            sb.append("\n  ").append(s.toString());
        }
        if (!products.isEmpty()) {
            sb.append("\nProducts Included:");
            // Productos: con precio, aplicando descuento extra (15% por servicio)
            double totalPrice = 0.0;
            for (Product p : this.getProducts()) {
                totalPrice += p.getPrice();
                sb.append("\n  ").append(p.toString());
            }

            double extraRate = this.getExtraDiscountRate();
            double extraDiscount = totalPrice * extraRate;
            double finalPrice = totalPrice - extraDiscount;
            sb.append("  Total price: ").append(String.format(Locale.US, "%.1f", totalPrice)).append("\n");
            sb.append("  Extra Discount from services:").append(String.format(Locale.US, "%.1f", extraDiscount)).append(" **discount -").append(extraDiscount).append("\n");
            sb.append("  Total discount: ").append(String.format(Locale.US, "%.1f", extraDiscount)).append("\n");
            sb.append("  Final Price: ").append(String.format(Locale.US, "%.1f", finalPrice));
        }
        return sb.toString();
    }

    /**
     * The method removes the ticket with the id given
     *
     * @param prodId the id of the product, given by parameter
     * @return returns the product if it has been removed successfully, else returns null
     */
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

    /**
     * The method returns the String with a specific format
     *
     * @return the string with the specific format
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

        csvPrinter.printRecord("EnterpriseMixedTicket", id, status);
        for (Product p : products) {
            if (p instanceof PersonalizedProduct) {
                PersonalizedProduct pp = (PersonalizedProduct) p;
                csvPrinter.printRecord(id, "PersonalizedProduct", pp.getId(), pp.getName(), pp.getCategory().name(), pp.getPrice(), pp.getMaxPers(), pp.getPerstonalizations());
            } else if (p instanceof BasicProduct) {
                BasicProduct pp = (BasicProduct) p;
                csvPrinter.printRecord(id, "BasicProduct", pp.getId(), pp.getName(), pp.getCategory().name(), pp.getPrice());
            } else if (p instanceof Meeting) {
                Meeting m = (Meeting) p;
                csvPrinter.printRecord(id, "Meeting", m.getId(), m.getName(), m.getPrice(), m.getExpiryDate().toLocalDate());
            } else if (p instanceof Food) {
                Food m = (Food) p;
                csvPrinter.printRecord(id, "Food", m.getId(), m.getName(), m.getPrice(), m.getExpiryDate().toLocalDate());
            }
        }
        for (Service s : services) {
            csvPrinter.printRecord(id, "Service", s.getMaxUseDate(), s.getName(), s.getId());
        }

    }
}