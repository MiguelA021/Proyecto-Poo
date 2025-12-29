package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.CustomerTicket;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Service.Service;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket.EnterpriseMixedTicket;
import upm.etsisi.poo.es.Ticket.EnterpriseServiceTicket;
import upm.etsisi.poo.es.Ticket.Ticket;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;

import java.time.LocalDate;


public class TicketCommand implements Command {

    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public String getDescription() {
        return "ticket add|remove|print|new ...  - ticket management";
    }

    @Override
    public boolean execute(String fullLine, String[] args) {
        Store store = Store.getInstance();

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
                // si tu "cash tickets" ya lista tickets por cajero, puedes dejar esto desactivado
                System.out.println(INCORRECT);
                break;

            case "new":
                ticketNew(args);
                break;

            default:
                System.out.println(INCORRECT);
                break;
        }

        return false;
    }

    private void ticketAdd(String[] args, Store store) {
        if (args.length < 4) {
            System.out.println(INCORRECT);
            return;
        }

        try {
            int ticketId = Integer.parseInt(args[2]);
            int cashId = Integer.parseInt(args[3].replace("UW", ""));

            CashierController cashierController = CashierController.getInstance();
            Cashier cashier = cashierController.searchCasherById(cashId);
            if (cashier == null) {
                System.out.println(Store.CASHIER_NOT_FOUND);
                return;
            }

            Ticket t = cashier.getTicketById(ticketId);
            if (t == null) {
                System.out.println(NOTEXIST);
                return;
            }

            // -------------------------
            // CASE 1: ADD SERVICE
            // ticket add <ticketId> <cashId> --s <YYYY-MM-DD>
            // -------------------------
            if (args.length == 6 && args[4].equalsIgnoreCase("--s")) {
                LocalDate maxUseDate = LocalDate.parse(args[5]);
                Service s = new Service(maxUseDate);

                boolean ok = false;
                if (t instanceof EnterpriseServiceTicket) {
                    ok = ((EnterpriseServiceTicket) t).addService(s);
                } else if (t instanceof EnterpriseMixedTicket) {
                    ok = ((EnterpriseMixedTicket) t).addService(s);
                } else {
                    System.out.println(INCORRECT);
                    return;
                }

                if (!ok) {
                    System.out.println(INCORRECT);
                    return;
                }

                System.out.println("ticket add: ok");
                return;
            }

            // -------------------------
            // CASE 2: ADD PRODUCT
            // ticket add <ticketId> <cashId> <prodId> <amount> [--p...]
            // -------------------------
            if (args.length < 6) {
                System.out.println(INCORRECT);
                return;
            }

            int prodId = Integer.parseInt(args[4]);
            int amount = Integer.parseInt(args[5]);

            Product product = store.getProduct(prodId);
            if (product == null) {
                System.out.println(NOTEXIST);
                return;
            }

            // CustomerTicket: comportamiento legacy (incluye personalizados)
            if (t instanceof CustomerTicket) {
                CustomerTicket customerTicket = (CustomerTicket) t;

                if (product instanceof PersonalizedProduct) {
                    PersonalizedProduct local = new PersonalizedProduct(
                            prodId,
                            product.getName(),
                            ((PersonalizedProduct) product).getCategory(),
                            product.getPrice(),
                            amount
                    );

                    for (int i = 6; i < args.length; i++) {
                        String personalization = args[i].replaceAll("--p", "");
                        local.addPersonalized(personalization);
                    }
                    local.newPrice();

                    customerTicket.ticketAdd(local, amount);
                } else {
                    customerTicket.ticketAdd(product, amount);
                }

                return;
            }

            // EnterpriseMixedTicket: añade productos (amount veces)
            if (t instanceof EnterpriseMixedTicket) {
                EnterpriseMixedTicket mt = (EnterpriseMixedTicket) t;

                for (int i = 0; i < amount; i++) {
                    mt.addProduct(product);
                }

                System.out.println("ticket add: ok");
                return;
            }

            // EnterpriseServiceTicket (solo servicios) u otros: no aceptan productos
            System.out.println(INCORRECT);

        } catch (Exception e) {
            // incluye NumberFormatException y parse de LocalDate
            System.out.println(INCORRECT);
        }
    }



    private void ticketRemove(String[] args, Store store) {
        if (args.length != 5) {
            System.out.println(INCORRECT);
            return;
        }

        try {
            int ticketId = Integer.parseInt(args[2]);
            int cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
            int prodId = Integer.parseInt(args[4]);

            CashierController cashierController = CashierController.getInstance();
            Cashier cashier = cashierController.searchCasherById(cashId);
            if (cashier == null) {
                System.out.println(Store.CASHIER_NOT_FOUND);
                return;
            }

            Ticket t = cashier.getTicketById(ticketId);
            if (t == null) {
                System.out.println(NOTEXIST);
                return;
            }

            if (!(t instanceof CustomerTicket)) {
                System.out.println(INCORRECT);
                return;
            }
            CustomerTicket customerTicket = (CustomerTicket) t;

            Product product = customerTicket.ticketRemove(prodId);
            if (product == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(customerTicket.ticketPrint(false)); // legacy ok por ahora
                System.out.println("ticket remove: ok");
            }

        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketPrint(String[] args, Store store) {
        if (args.length != 4) {
            System.out.println(INCORRECT);
            return;
        }

        try {
            int ticketId = Integer.parseInt(args[2]);
            int cashId = Integer.parseInt(args[3].replaceAll("UW", ""));

            CashierController cashierController = CashierController.getInstance();
            Cashier cashier = cashierController.searchCasherById(cashId);
            if (cashier == null) {
                System.out.println(Store.CASHIER_NOT_FOUND);
                return;
            }

            Ticket ticket = cashier.getTicketById(ticketId);
            if (ticket == null) {
                System.out.println(NOTEXIST);
                return;
            }

            String printed = ticket.getPrinter().print(ticket, true);
            if (printed == null || printed.isEmpty()) {
                System.out.println(EMPTY_TICKET);
            } else {
                System.out.println(printed);
                System.out.println("ticket print: ok");
            }

        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void ticketNew(String[] args) {
        // ticket new [<id>] <cashId> <userId> -[c|p|s]   (default -p)

        if (args.length < 4 || args.length > 6) {
            System.out.println(INCORRECT);
            return;
        }

        Integer ticketId = null;
        int cashId;
        String userId;
        String mode = "-p"; // default

        try {
            if (args.length == 4 || args.length == 5) {
                // ticket new <cashId> <userId> [-mode]
                cashId = Integer.parseInt(args[2].replaceAll("UW", ""));
                userId = args[3];
                if (args.length == 5) {
                    mode = args[4];
                }
            } else {
                // ticket new <id> <cashId> <userId> <mode>
                ticketId = Integer.valueOf(args[2]);
                cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
                userId = args[4];
                mode = args[5];
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            return;
        }

        if (!mode.equals("-p") && !mode.equals("-s") && !mode.equals("-c")) {
            System.out.println(INCORRECT);
            return;
        }

        CashierController cashierController = CashierController.getInstance();
        Cashier cashier = cashierController.searchCasherById(cashId);
        if (cashier == null) {
            System.out.println(Store.CASHIER_NOT_FOUND);
            return;
        }

        // 🔹 Si no nos dan id → lo generamos aquí (YA NO en Cashier)
        if (ticketId == null) {
            ticketId = (int) (Math.random() * 100000);
            while (cashier.hasTicketId(ticketId)) {
                ticketId = (int) (Math.random() * 100000);
            }
        }

        // Determinar tipo de usuario:
        // si el último carácter NO es dígito → empresa
        boolean isEnterprise = !Character.isDigit(userId.charAt(userId.length() - 1));

        Ticket newTicket;

        if (!isEnterprise) {
            // 👤 Cliente normal → solo -p
            if (!mode.equals("-p")) {
                System.out.println(INCORRECT);
                return;
            }
            newTicket = new CustomerTicket(ticketId);
        } else {
            // 🏢 Empresa
            if (mode.equals("-s")) {
                newTicket = new EnterpriseServiceTicket(ticketId);
            } else if (mode.equals("-c")) {
                newTicket = new EnterpriseMixedTicket(ticketId);
            } else {
                // empresa no puede usar -p
                System.out.println(INCORRECT);
                return;
            }
        }

        cashier.addTicket(newTicket);
        System.out.println("ticket new: ok");
    }

}
