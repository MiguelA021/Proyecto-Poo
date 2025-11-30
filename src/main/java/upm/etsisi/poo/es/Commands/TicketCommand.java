package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;

public class TicketCommand implements Command {

    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public String getDescription() {
        return "ticket add|remove|print ...  - ticket management";
    }

    @Override
    public boolean execute(String fullLine, String[] args, Store store) {
        if (args.length < 2) {
            System.out.println(INCORRECT);
            return false;
        }

        String sub = args[1];

        switch (sub) {
            case "add":
                ticketAdd(args, store);
                break;
            case "remove":
                ticketRemove(args, store);
                break;
            case "print":
                ticketPrint(args, store);
                break;
            case "list":
                store.ticketList();
                break;
            case "new":
                ticketNew(args, store);
                break;
            default:
                System.out.println(INCORRECT);
                break;
        }

        return false;
    }

    private void ticketAdd(String[] args, Store store) {
        if (args.length < 6) {
            System.out.println(INCORRECT);
            return;
        }
        try {

            int ticketId = Integer.parseInt(args[2]);
            String casherId = args[3];
            int casherIdGood = Integer.parseInt(casherId.replace("UW", ""));
            int prodId = Integer.parseInt(args[4]);
            int amount = Integer.parseInt(args[5]);
            Cashier cashier = store.searchCasherById(casherIdGood);
            Ticket ticket = cashier.getTicketById(ticketId);
            Product product = store.getProduct(prodId);

            if(product instanceof PersonalizedProduct){
                PersonalizedProduct personalizedProduct  = (PersonalizedProduct) product;
                PersonalizedProduct local = new PersonalizedProduct(prodId, product.getName(), ((PersonalizedProduct) product).getCategory(),product.getPrice(), amount);
                for (int i = 6; i < args.length; i++) {
                    String personalization = args[i].replaceAll("--p", "");
                    local.addPersonalized(personalization);
                }
                local.newPrice();
                personalizedProduct.newPrice();
                ticket.ticketAdd(local, store, amount);
            }else {
                ticket.ticketAdd(product, store, amount);
            }

        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketRemove(String[] args, Store store) {
        if ( args.length != 5) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int ticketId = Integer.parseInt(args[2]);
            int prodId = Integer.parseInt(args[4]);
            String casherId = args[3];
            int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
            Cashier cashier = store.searchCasherById(casherIdGood);
            Ticket ticket = cashier.getTicketById(ticketId);
            Product product = ticket.ticketRemove(prodId);
            if (product == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(ticket.ticketPrint(false));
                System.out.println("ticket remove: ok");
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketPrint(String[] args, Store store) {
        int ticketId = Integer.parseInt(args[2]);
        String casherId = args[3];
        int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
        Cashier cashier = store.searchCasherById(casherIdGood);
        Ticket ticket = cashier.getTicketById(ticketId);
        String printed = ticket.ticketPrint(true);
        if (printed.isEmpty()) {
            System.out.println(EMPTY_TICKET);
        } else {
            System.out.println(printed);
            System.out.println("ticket print: ok");
        }
    }

    private void ticketNew(String[] args, Store store) {
        if (args.length != 4 && args.length != 5) {
            System.out.println(INCORRECT);
            return;
        }

        Integer ticketId = null;
        int cashId;
        int userId; // de momento solo lo leemos creo

        try {
            if (args.length == 4) {
                // ticket new <cashId> <userId>
                ticketId = null;
                String cashierId = args[2].replaceAll("UW", "");
                cashId = Integer.parseInt(cashierId);
                userId = store.dniToId(args[3]);
            } else {
                // ticket new <id> <cashId> <userId>

                String cashierId = args[3].replaceAll("UW", "");
                ticketId = Integer.valueOf(args[2]);
                cashId = Integer.parseInt(cashierId);
                userId = store.dniToId(args[4]);
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            return;
        }

        store.addTicketOnCashier(ticketId, cashId, userId);

    }
}
