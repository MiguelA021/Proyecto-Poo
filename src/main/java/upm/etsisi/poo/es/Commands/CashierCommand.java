package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.User.Cashier;

public class CashierCommand implements Command {

    @Override
    public String getName() {
        return "cash";
    }


    @Override
    public String getDescription() {
        return "cashier add|remove|list|tickets ...  - cashier management";
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
                cashierAdd(args, store);
                break;
            case "remove":
                cashierRemove(args, store);
                break;
            case "list":
                list(store);
                break;
            case "tickets":
                cashTickets(args, store);
                break;
            default:
                System.out.println(INCORRECT);
        }

        return false;
    }

    private void cashierAdd(String[] args, Store store) {
        if (args.length != 5 && args.length != 4) {
            System.out.println(INCORRECT);
        }
        try {
            int cashId;
            String name;
            String email;
            if (args.length == 4) {
                name = args[2];
                email = args[3];
                store.addCasher(null, name, email);
            } else {
                String casherId = args[2].replaceAll("UW", "");
                int cash = Integer.parseInt(casherId);
                name = args[3];
                email = args[4];
                store.addCasher(cash, name, email);
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            return;
        }

    }

    private void cashierRemove(String[] args, Store store) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            String casherId = args[2].replaceAll("UW", "");
            int cash = Integer.parseInt(casherId);
            store.removeCasher(cash);

        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            return;
        }
    }

    private void list(Store store) {
        store.listCashers();
    }

    private void cashTickets(String[] args, Store store) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
          String casherId = args[2].replaceAll("UW", "");
            int cash = Integer.parseInt(casherId);
            Cashier casher = store.searchCasherById(cash);
            casher.listTickets();
        } catch (NullPointerException e) {
            System.out.println(INCORRECT);
            return;
        }
    }
}
