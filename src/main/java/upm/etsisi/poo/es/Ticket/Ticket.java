package upm.etsisi.poo.es.Ticket;


import upm.etsisi.poo.es.Product.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Ticket {
    protected final Integer id;
    protected Status status;
    final static int MAX_PRODUCT = 100;
    Product[] productList;
    protected ArrayList<LocalDateTime> dates;
    protected int tickId;
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
    int amount;

    protected Ticket(Integer id) {
        this.id = id;
        this.amount = 0;
        this.status = Status.EMPTY;
        this.productList = new Product[MAX_PRODUCT];
        this.dates = new ArrayList<LocalDateTime>();
        LocalDateTime now = LocalDateTime.now();
        dates.add(now);
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

    protected String toStringId() {
        StringBuilder resul = new StringBuilder();
        String status = this.status.toString().toUpperCase();
        switch (status) {
            case "EMPTY":
                String inicio = dates.get(0).format(DATE_FORMAT);
                resul.append(inicio).append("-").append(tickId);
                break;
            case "OPEN":
                resul.append(tickId);
                break;
            case "CLOSED":
                String fin = dates.get(1).format(DATE_FORMAT);
                resul.append(tickId).append(fin);
                break;
            default:
                resul.append("ERROR, status is undefined");
                break;
        }
        return resul.toString();
    }

}
