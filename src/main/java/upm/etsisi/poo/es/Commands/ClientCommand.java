package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;

public class ClientCommand implements Command {
    @Override
    public String getName() {
        return "User";
    }

    @Override
    public String getDescription() {
        return "client add|list|update|remove ...  - product management";
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
                clientAdd(args, store);
                break;
            case "remove":
                remove(args, store);
                break;
            case "list":
                list(store);
                break;
            default:
                System.out.println(INCORRECT);
        }

        return false;
    }

    private void clientAdd(String[] args, Store store) {
        if (args.length != 6) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            String name = args[2];
            String Dni = args[3];
            String email = args[4];
            String casherId = args[5];
            String casherIdGood = "";
            for (int i = 2; i < casherId.length(); i++) {
                casherIdGood.concat(Character.toString(casherId.charAt(i)));
            }
            store.addCustomer(name, Dni, email, Integer.parseInt(casherIdGood));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void remove(String[] args, Store store) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            String dni = args[2];
            boolean removed = store.removeCustomer(dni);
            if (!removed) {
                System.out.println("could not find the client");
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void list(Store store) {
        store.listCustomers();
    }
}
