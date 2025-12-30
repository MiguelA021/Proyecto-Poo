package upm.etsisi.poo.es.Ticket.print;

import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Service.Service;
import upm.etsisi.poo.es.Ticket.EnterpriseMixedTicket;
import upm.etsisi.poo.es.Ticket.Ticket;

import java.util.Locale;

public class EnterpriseMixedTicketPrinter implements TicketPrinter {

    @Override
    public String print(Ticket ticket, boolean close) {
        EnterpriseMixedTicket t = (EnterpriseMixedTicket) ticket;

        if (close) t.close(); // solo cerrará si hay >=1 producto y >=1 servicio

        StringBuilder sb = new StringBuilder();
        sb.append("Ticket : ").append(t.getId()).append("\n");

        // Servicios: sin precio
        for (Service s : t.getServices()) {
            sb.append("  ").append(s.toString()).append("\n");
        }

        // Productos: con precio, aplicando descuento extra (15% por servicio)
        double totalPrice = 0.0;
        for (Product p : t.getProducts()) {
            if (p != null) totalPrice += p.getPrice();
        }

        double extraRate = t.getExtraDiscountRate();
        double extraDiscount = totalPrice * extraRate;
        double finalPrice = totalPrice - extraDiscount;

        for (Product p : t.getProducts()) {
            if (p != null) {
                sb.append("  ").append(p.toString()).append("\n");
            }
        }

        sb.append("  Total price: ").append(String.format(Locale.US, "%.3f", totalPrice)).append("\n");
        sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", extraDiscount)).append("\n");
        sb.append("  Final price: ").append(String.format(Locale.US, "%.3f", finalPrice));

        return sb.toString();
    }
}
