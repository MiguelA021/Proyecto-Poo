package upm.etsisi.poo.es.Tickets;


import upm.etsisi.poo.es.Product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Entity
@Table(name = "tickets")
public abstract class Ticket {
    @Id
    @Column(name = "Id")
    protected Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    protected Status status;

    @Transient
    final static int MAX_PRODUCT = 100;
    @Transient
    Product[] productList;

    @Column(name = "dates")
    protected LocalDateTime dates;
    @Transient
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    @Column(name = "amount")
    int amount;

    public static final String ERROR_FULL = "ERROR: Full Ticket (100 products max)";
    protected static final String ERROR_PRODUCT_ID_NOT_FOUND = "ERROR: Product ID not found";
    protected static final String ADD_OK = "ticket add: ok";
    protected static final String MANY_PEOPLE = "Too many people";
    protected static final String PERIOD_NOT_VALID = "The period of time is not valid";
    protected static final String NO_PRODUCTS_IN_THE_TICKET = "ERROR: No products in the ticket";
    protected static final String PRODUCT_DOES_NOT_EXIST = "ERROR: this product does not exist.";
    protected static final String ERROR_TICKET_CLOSE = "ERROR: the ticket is closed. It can't be modified";
    protected static final String DONT_CLOSE_NOT_VALID_TIME = "The ticket can`t be closed because some event's period of time is invalid. \n";
    @Transient
    Comparator<Product> nameComp = Comparator.comparing(Product::getName);
    protected static final String TOTAL_PRICE = "Total price:";
    protected static final String TOTAL_DISCOUNT = "Total discount:";
    protected static final String FINAL_PRICE = "Final price:";
    protected static final String TICKET = "Ticket :";
    protected static final String TICKET_NEW_OK = "ticket new: ok";

    public Ticket() {
        //requerido por hibernate
    }

    protected Ticket(Integer id) {
        this.id = id;
        this.amount = 0;
        this.status = Status.EMPTY;
        this.productList = new Product[MAX_PRODUCT];
        this.dates = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public abstract boolean canBeClosed();

    public boolean close() {

        if (status == Status.CLOSED) {
            return false;
        }
        if (!canBeClosed()) {
            return false;
        }

        status = Status.CLOSED;
        return true;
    }

    public String print(boolean close) {
        return "";
    }

    public String formatList() {// si esta abierto mostramos solo id. Si esta vacio mostramos fecha de creacion.
        // Si esta cerrado fecha de cierre
        StringBuilder resul = new StringBuilder();

        resul.append("  " + toStringId()).append(" - ").append(this.status.toString().toUpperCase());
        return resul.toString();
    }

    public String toStringNew() {
        StringBuilder sc = new StringBuilder(); // Soy Aaron, lo de format() esta puesto para que siga el formato que
        // buscamos de fecha.
        // te lo pongo para que asi no te comas la cabeza con eso. Por lo demás ya te
        // dejo que sigas con ello
        sc.append(TICKET + " " + this.id + "\n");
        sc.append("  " + TOTAL_PRICE + " 0.0 \n");
        sc.append("  " + TOTAL_DISCOUNT + " 0.0 \n");
        sc.append("  " + FINAL_PRICE + " 0.0 \n");
        sc.append(TICKET_NEW_OK);
        return sc.toString();
    }

    protected String toStringId() {
        StringBuilder resul = new StringBuilder();
        String status = this.status.toString().toUpperCase();
        switch (status) {
            case "EMPTY":
                String inicio = dates.format(DATE_FORMAT);
                resul.append(inicio).append("-").append(id);
                break;
            case "OPEN":
                resul.append(id);
                break;
            case "CLOSED":
                String fin = dates.format(DATE_FORMAT);
                resul.append(id).append(fin);
                break;
            default:
                resul.append("ERROR, status is undefined");
                break;
        }
        return resul.toString();
    }

}
