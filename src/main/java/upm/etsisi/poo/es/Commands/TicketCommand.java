package upm.etsisi.poo.es.Commands;


import upm.etsisi.poo.es.Tickets.*;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;


import java.util.HashMap;
import java.util.Map;

public class TicketCommand implements Command {
    private TicketController ticketController;

    public TicketCommand() {
        this.ticketController = new TicketController();
    }

    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public boolean execute(String fullLine, String[] args) {
        if (args.length < 2) {
            System.out.println(INCORRECT);
            return false;
        }

        String sub = args[1];

        switch (sub) {
            case "add":
                ticketAdd(args);
                break;
            case "remove":
                ticketRemove(args);
                break;
            case "print":
                ticketPrint(args);
                break;
            case "list":
                ticketList();
                break;
            case "new":
                ticketController.ticketNew(args);
                break;
            default:
                System.out.println(INCORRECT);
                break;
        }

        return false;
    }

    private void ticketAdd(String[] args) {
        if (args.length < 4) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int ticketId = Integer.parseInt(args[2]);
            int casherId = Integer.parseInt(args[3].replace("UW", ""));

            if (CashierController.getInstance().exitsTicket(casherId, ticketId)) {
                ticketController.prodAdd(args);
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketRemove(String[] args) {
        if (args.length != 5) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int ticketId = Integer.parseInt(args[2]);
            int prodId = Integer.parseInt(args[4]);
            int casherId = Integer.parseInt(args[3].replace("UW", ""));

            if (CashierController.getInstance().exitsTicket(casherId, ticketId)) {
                ticketController.ticketRemove(ticketId, prodId);
            } else {
                System.out.println("ticket does not exist");
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketPrint(String[] args) {
        if(args.length <4){
            System.out.println(INCORRECT);
            return;
        }
        int ticketId = Integer.parseInt(args[2]);
        String casherId = args[3];
        int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));
        if (CashierController.getInstance().exitsTicket(casherIdGood, ticketId))
            ticketController.ticketPrint(ticketId);
    }



    private void ticketList() {
        CashierController custC = CashierController.getInstance();
        HashMap<Integer, Cashier> cashers = custC.getMap();
        System.out.println("Ticket list: ");

        for (Map.Entry<Integer, Cashier> entry : cashers.entrySet()) {
            Cashier casher = entry.getValue();
            System.out.print(casher.listTickets());
        }
        System.out.println("ticket list: ok");
    }


}
