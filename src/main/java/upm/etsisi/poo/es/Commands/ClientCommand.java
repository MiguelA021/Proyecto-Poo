package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.User.Customer;
import upm.etsisi.poo.es.User.CustomerController;

public class ClientCommand implements Command {
    @Override
    public String getName() {
        return "client";
    }


    @Override
    public boolean execute(String fullLine, String[] args) {
      CustomerController customerController = CustomerController.getInstance();
        if (args.length < 2) {
            System.out.println(INCORRECT);
            return false;
        }

        String sub = args[1];

        switch (sub) {
            case "add":
                clientAdd(args, customerController);
                break;
            case "remove":
                remove(args,customerController);
                break;
            case "list":
                list(customerController);
                break;
            default:
                System.out.println(INCORRECT);
        }

        return false;
    }

    private void clientAdd(String[] args, CustomerController customerController) {
        if (args.length != 6) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            String name = args[2];
            String Dni = args[3];
            String email = args[4];
            String casherId = args[5];
            int casherIdGood = Integer.parseInt(casherId.replaceAll("UW", ""));

            customerController.addCustomer(name, Dni, email,casherIdGood);
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void remove(String[] args,CustomerController customerController) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            String dni = args[2];
            boolean removed = customerController.removeCustomer(dni);
            if (!removed) {
                System.out.println("could not find the client");
            }else {
                System.out.println("client remove: ok");
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void list(CustomerController customerController) {
        customerController.listCustomers();
    }
}
