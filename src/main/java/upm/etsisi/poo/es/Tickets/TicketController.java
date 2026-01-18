package upm.etsisi.poo.es.Tickets;

import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.User.*;

import static upm.etsisi.poo.es.Commands.Command.*;

public class TicketController {
    private static TicketController instance;
    private final TicketData ticketData;

    public static TicketController getInstance() {
        if (instance == null) {
            instance = new TicketController();
        }
        return instance;
    }

    private TicketController() {
        this.ticketData = TicketData.getInstance();
    }

    /**
     * The method adds the product into the ticket,
     *
     * @param args arguments such as ids or amounts, it must follow the syntax of
     *             the command. If not, it won't add it.
     */
    public void prodAdd(String[] args) {
        try {
            int ticketId = Integer.parseInt(args[2]);
            Ticket ticket = TicketData.getInstance().getTicket(ticketId);
            if (ticket.getStatus() != Status.CLOSE) {
                // -------------------------
                // CASE 1: ADD SERVICE
                // ticket add <ticketId> <cashId> <serviceId>
                // -------------------------

                if (args.length == 5) {
                    int idProducto = Integer.parseInt(args[4].replace("S", "")) * -1;
                    Service s = (Service) ProductController.getInstance().getProduct(idProducto);
                    boolean ok = false;
                    if (ticket instanceof EnterpriseServiceTicket) {
                        ok = ((EnterpriseServiceTicket) ticket).addService(s);
                        EnterpriseServiceTicket enterpriseServiceTicket = (EnterpriseServiceTicket) ticket;
                        System.out.println(enterpriseServiceTicket.print(false));
                    } else if (ticket instanceof EnterpriseMixedTicket) {
                        ok = ((EnterpriseMixedTicket) ticket).addService(s);
                        EnterpriseMixedTicket enterpriseMixedTicket = (EnterpriseMixedTicket) ticket;
                        System.out.println(enterpriseMixedTicket.print(false));
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
                Product product = ProductController.getInstance().getProduct(prodId);
                if (product == null) {
                    System.out.println(NOTEXIST);
                    return;
                }
                // CustomerTicket: comportamiento legacy (incluye personalizados)
                if (ticket instanceof CustomerTicket) {
                    CustomerTicket customerTicket = (CustomerTicket) ticket;

                    if (product instanceof PersonalizedProduct) {
                        int maxPers = ((PersonalizedProduct) product).getMaxPers();
                        PersonalizedProduct local = new PersonalizedProduct(
                                prodId,
                                product.getName(),
                                ((PersonalizedProduct) product).getCategory(),
                                product.getPrice(),
                                maxPers
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
                if (ticket instanceof EnterpriseMixedTicket) {
                    EnterpriseMixedTicket mt = (EnterpriseMixedTicket) ticket;

                    if (product instanceof PersonalizedProduct) {
                        int maxPers = ((PersonalizedProduct) product).getMaxPers();
                        PersonalizedProduct local = new PersonalizedProduct(
                                prodId,
                                product.getName(),
                                ((PersonalizedProduct) product).getCategory(),
                                product.getPrice(),
                                maxPers
                        );
                        for (int i = 6; i < args.length; i++) {
                            String personalization = args[i].replaceAll("--p", "");
                            local.addPersonalized(personalization);
                        }
                        local.newPrice();
                        mt.addProduct(local, amount);
                    } else {
                        mt.addProduct(product, amount);
                    }
                    return;
                }
                // EnterpriseServiceTicket (solo servicios) u otros: no aceptan productos

                System.out.println(INCORRECT);
            }else{
                System.out.println("Ticket already closed");
            }
        } catch (NumberFormatException e) {
            //TODO catch all exceptions
            System.out.println(INCORRECT);
        }
    }


    /**
     * The method removes the product from the ticket, both given by their ids
     *
     * @param ticketId the id of the ticket, given by parameter
     * @param prodId   the id of the product, given by parameter
     */
    public void ticketRemove(int ticketId, int prodId) {
        Ticket t = TicketData.getInstance().getTicket(ticketId);
        if (t instanceof CustomerTicket) {
            Product customerProduct = ((CustomerTicket) t).ticketRemove(prodId);
            if (customerProduct == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(((CustomerTicket) t).print(false));
            }
        } else if (t instanceof EnterpriseServiceTicket) {
            Product serviceProduct = ((EnterpriseServiceTicket) t).ticketRemove(prodId);
            if (serviceProduct == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(((EnterpriseServiceTicket) t).print(false));
            }
        } else if (t instanceof EnterpriseMixedTicket) {
            Product mixedProduct = ((EnterpriseMixedTicket) t).ticketRemove(prodId);
            if (mixedProduct == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(((EnterpriseMixedTicket) t).print(false));
            }
        }
    }

    /**
     * The method adds a new ticket into the customer and also the cashier both
     * given by parameter
     *
     * @param args arguments such as ids or choices, it must follow the syntax of
     *             the command. If not, it won't add it.
     */
    public void ticketNew(String[] args) {
        if (args.length != 4 && args.length != 5 && args.length != 6) {
            System.out.println(INCORRECT);
            return;
        }
        Integer ticketId;
        int cashId;
        int userId;
        // ticket new [<id>] <cashId> <userId> -[c|p|s]
        try {
            userId = CustomerController.getInstance().dniToId(args[3]);
            Customer customer = CustomerController.getInstance().getCustomer(userId);
            if (customer == null) {
                if (args.length > 4) {
                    userId = CustomerController.getInstance().dniToId(args[4]);
                }
                customer = CustomerController.getInstance().getCustomer(userId);
                if (customer == null) {
                    System.out.println("User not found");
                    return;
                }
            }
            if (customer instanceof CustomerEnterprise) {
                if (args.length == 5) {
                    // ticket new [<id>] <cashId> <userId> -[c|p|s]
                    cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
                    Cashier cashier = CashierController.getInstance().searchCasherById(cashId);
                    if (cashier == null) {
                        System.out.println("Cashier not found");
                        return;
                    }
                    switch (args[4]) {
                        case "-c":
                            ticketId = TicketData.getInstance().addTicket("combined");
                            CashierController.getInstance().addTicket(ticketId, cashId);
                            CustomerController.getInstance().addTicket(ticketId, userId);
                            break;
                        case "-s":
                            ticketId = TicketData.getInstance().addTicket("services");
                            CashierController.getInstance().addTicket(ticketId, cashId);
                            CustomerController.getInstance().addTicket(ticketId, userId);
                            break;
                        default:
                            System.out.println(INCORRECT);
                            break;
                    }

                } else if (args.length == 6) {
                    // ticket new [<id>] <cashId> <userId> -[c|p|s]
                    ticketId = Integer.valueOf(args[2]);
                    cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
                    Cashier cashier = CashierController.getInstance().searchCasherById(cashId);
                    if (cashier == null) {
                        System.out.println("Cashier not found");
                        return;
                    }
                    switch (args[5]) {
                        case "-s":
                            if (!TicketData.getInstance().addTicket(ticketId, "services")) {
                                System.out.println(ID_REPEAT);
                            } else {
                                CashierController.getInstance().addTicket(ticketId, cashId);
                                CustomerController.getInstance().addTicket(ticketId, userId);
                            }
                            break;
                        case "-c":
                            if (!TicketData.getInstance().addTicket(ticketId, "combined")) {
                                System.out.println(ID_REPEAT);
                            } else {
                                CashierController.getInstance().addTicket(ticketId, cashId);
                                CustomerController.getInstance().addTicket(ticketId, userId);
                            }
                            break;
                        default:
                            System.out.println(INCORRECT);
                            break;
                    }
                } else {
                    System.out.println(INCORRECT);

                }
            } else {
                if (args.length == 4) {
                    // ticket new <cashId> <userId>
                    String cashierId = args[2].replaceAll("UW", "");
                    cashId = Integer.parseInt(cashierId);

                    Cashier cashier = CashierController.getInstance().searchCasherById(cashId);
                    if (cashier == null) {
                        System.out.println("Cashier not found");
                        return;
                    }
                    ticketId = TicketData.getInstance().addTicket("products");
                    CashierController.getInstance().addTicket(ticketId, cashId);
                    CustomerController.getInstance().addTicket(ticketId, userId);
                } else {
                    // ticket new <id> <cashId> <userId>
                    String cashierId = args[3].replaceAll("UW", "");
                    cashId = Integer.parseInt(cashierId);
                    Cashier cashier = CashierController.getInstance().searchCasherById(cashId);
                    if (cashier == null) {
                        System.out.println("Cashier not found");
                        return;
                    }
                    ticketId = Integer.valueOf(args[2]);
                    if (!TicketData.getInstance().addTicket(ticketId, "products"))
                        System.out.println(ID_REPEAT);
                    else {
                        CashierController.getInstance().addTicket(ticketId, cashId);
                        CustomerController.getInstance().addTicket(ticketId, userId);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }

    }

    /**
     * The method prints the ticket if it's not empty
     *
     * @param idTicket the id of the ticket given by parameter
     */
    public void ticketPrint(int idTicket) {
        Ticket ticket = ticketData.getTicket(idTicket);
        if (ticket != null) {
            String data = ticket.print(true);
            if (data.isEmpty()) {
                System.out.println(EMPTY_TICKET);
            } else {
                System.out.println(data);
                System.out.println("ticket print: ok");
            }
        }
    }
}
