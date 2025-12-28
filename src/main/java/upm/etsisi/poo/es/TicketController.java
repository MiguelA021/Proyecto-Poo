package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;

import static upm.etsisi.poo.es.Commands.Command.*;

public class TicketController {
    private TicketData ticketData;

    public TicketController() {
        this.ticketData = TicketData.getInstance();
    }


    public void prodAdd(String[] args) {
        try {
            int prodId = Integer.parseInt(args[4]);
            int amount = Integer.parseInt(args[5]);
            int ticketId = Integer.parseInt(args[2]);

            Ticket ticket = ticketData.getTicket(ticketId);
            if(ticket != null) {
                Store store = Store.getInstance();
                Product product = store.getProduct(prodId);
                if (product instanceof PersonalizedProduct) {
                    PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
                    PersonalizedProduct local = new PersonalizedProduct(prodId, product.getName(),
                            ((PersonalizedProduct) product).getCategory(), product.getPrice(), amount);
                    for (int i = 6; i < args.length; i++) {
                        String personalization = args[i].replaceAll("--p", "");
                        local.addPersonalized(personalization);
                    }
                    local.newPrice();
                    personalizedProduct.newPrice();
                    ticket.ticketAdd(local, amount);
                } else {
                    ticket.ticketAdd(product, amount);
                }
            }
        }catch (NumberFormatException ex){
            System.out.println(INCORRECT);
        }
    }

    public void ticketRemove(int ticketId, int prodId) {

        Ticket ticket = ticketData.getTicket(ticketId);
        if (ticket != null) {
            Product product = ticket.ticketRemove(prodId);
            if (product == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(ticket.ticketPrint(false));
                System.out.println("ticket remove: ok");
            }
        }
    }
    public void ticketPrint(int idTicket){
        Ticket ticket = ticketData.getTicket(idTicket);
        if(ticket != null) {
            String data = ticket.ticketPrint(true);
            if (data.isEmpty()) {
                System.out.println(EMPTY_TICKET);
            } else {
                System.out.println(data);
                System.out.println("ticket print: ok");
            }
        }
    }
}
