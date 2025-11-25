package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.Casher;

public class TicketCommand implements Command {

    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public String getDescription() {
        return "ticket add|remove|print|list|new ...  - ticket management";
    }

    @Override
    public boolean execute(String fullLine, String[] args, Store store, Ticket ticket) {
        if (args.length < 2) {
            System.out.println(INCORRECT);
            return false;
        }

        String sub = args[1];

        switch (sub) {
            case "add":
                ticketAdd(args, store, ticket);
                break;
            case "remove":
                ticketRemove(args, ticket);
                break;
            case "print":
                ticketPrint(ticket);
                break;
            case "list":
                store.ticketList();
                break;
            case "new":
                ticketNew(args, store);
                break;
            default:
                System.out.println(INCORRECT);
        }

        return false;
    }

    private void ticketAdd(String[] args, Store store, Ticket ticket) {
        if (args.length != 4) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            int amount = Integer.parseInt(args[3]);
            ticket.ticketAdd(id, store, amount);
            // ticketAdd ya imprime y dice "ticket add: ok" o errores
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketRemove(String[] args, Ticket ticket) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            Product product = ticket.ticketRemove(id);
            if (product == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(product.toString());
                System.out.println("ticket remove: ok");
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketPrint(Ticket ticket) {
        String printed = ticket.ticketPrint();
        if (printed.isEmpty()) {
            System.out.println(EMPTY_TICKET);
        } else {
            System.out.println(printed);
            System.out.println("ticket print: ok");
        }
    }

    private void ticketNew(String[] args, Store store) {
        // Formatos válidos:
        //  ticket new <cashId> <userId>
        //  ticket new <id> <cashId> <userId>
        if (args.length != 4 && args.length != 5) {
            System.out.println(INCORRECT);
            return;
        }

        Integer ticketId = null;
        int cashId;
        String userId; // de momento solo lo leemos creo

        try {
            if (args.length == 4) {
                // ticket new <cashId> <userId>
                cashId = Integer.parseInt(args[2]);
                userId = args[3];
            } else {
                // ticket new <id> <cashId> <userId>
                ticketId = Integer.valueOf(args[2]);
                cashId = Integer.parseInt(args[3]);
                userId = args[4];
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            return;
        }

        Casher casher = store.getCasher(cashId);
        if (casher == null) {
            System.out.println(Store.CASHER_NOT_FOUND);
            return;
        }


        if (ticketId == null) {
            ticketId = casher.generateTicketId();
        } else if (casher.hasTicket(ticketId)) {
            System.out.println(ID_REPEAT);
            return;
        }

        Ticket t = new Ticket(ticketId);
        casher.addTicket(ticketId, t);

        System.out.println("ticket new: ok");
        System.out.println("Ticket id: " + ticketId + " (cashId: " + cashId + ", userId: " + userId + ")");
    }
}
